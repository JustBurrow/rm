/**
 *
 */
package kr.lul.urs.core.domain.entity;

import static kr.lul.urs.core.configuration.InjectionConstants.Beans.NAME_TRANSACTION_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.lul.urs.core.CoreTestConfig;
import kr.lul.urs.core.domain.ResourceFile;
import kr.lul.urs.core.domain.mapping.ResourceFileRevisionMapping;
import kr.lul.urs.core.service.internal.ResourceFileInternalService;
import kr.lul.urs.core.test.AbstractDomainTest;
import kr.lul.urs.core.test.ResourceFileDomainUtils;

/**
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 5. 10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CoreTestConfig.class })
@Transactional(transactionManager = NAME_TRANSACTION_MANAGER)
@Rollback(CoreTestConfig.ROLLBACK)
public class ResourceFileRevisionEntityTest extends AbstractDomainTest {
  @Autowired
  private ResourceFileInternalService resourceFileInternalService;

  private File                        f1     = getTestFile("test-01");
  private File                        f1copy = getTestFile("test-01-copy");
  private File                        f2     = getTestFile("test-01");

  @Before
  public void setUp() throws Exception {
    this.setAgentPlatformAsRandom();

    assertThat(this.f1).exists().isFile();
    assertThat(this.f1copy).exists().isFile();
    assertThat(this.f2).exists().isFile();
  }

  @Test
  public void testConstruct() throws Exception {
    // Given
    ResourceFile resourceFile = ResourceFileDomainUtils.create(this.platform, this.resourceFileInternalService);
    FileInputStream input = new FileInputStream(this.f1);
    String sha1 = DigestUtils.sha1Hex(input);
    input.close();

    // When
    ResourceFileRevisionEntity rev = new ResourceFileRevisionEntity(resourceFile, 1, sha1);

    // Then
    assertThat(rev.getId()).isNotNull()
        .hasFieldOrPropertyWithValue(ResourceFileRevisionMapping.Entity.RESOURCE_FILE, resourceFile.getId())
        .hasFieldOrPropertyWithValue(ResourceFileRevisionMapping.Entity.REVISION, 1);
    assertThat(rev.getOwner()).isEqualTo(this.operator);
    assertThat(rev.getAgentPlatform()).isEqualTo(resourceFile.getAgentPlatform());
    assertThat(rev.getResourceFile()).isEqualTo(resourceFile);
    assertThat(rev.getRevision()).isEqualTo(1);
    assertThat(rev.getName()).isEqualTo(resourceFile.getName());
    assertThat(rev.getSha1()).isEqualTo(DigestUtils.sha1Hex(new FileInputStream(this.f1)));
  }
}