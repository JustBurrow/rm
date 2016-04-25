package kr.lul.urs.core.service.internal;

import static com.spencerwi.hamcrestJDK8Time.matchers.IsAfter.after;
import static java.lang.String.format;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.lul.urs.application.AbstractApplicationTest;
import kr.lul.urs.application.ApplicationTestConfig;
import kr.lul.urs.application.configuration.InjectionConstants.Beans;
import kr.lul.urs.core.command.CreateResourceFileCmd;
import kr.lul.urs.core.command.ReadResourceFileCmd;
import kr.lul.urs.core.domain.Operator;
import kr.lul.urs.core.domain.ResourceFile;
import kr.lul.urs.core.domain.entity.ResourceFileEntity;
import kr.lul.urs.core.test.OperatorUtils;
import kr.lul.urs.core.test.ResourceFileUtils;
import kr.lul.urs.util.AssertionException;
import kr.lul.urs.util.Randoms;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { ApplicationTestConfig.class })
@Transactional(transactionManager = Beans.NAME_TRANSACTION_MANAGER)
@Rollback(ApplicationTestConfig.ROLLBACK)
public class ResourceFileInternalServiceTest extends AbstractApplicationTest {
  @Autowired
  private ResourceFileInternalService resourceFileInternalService;

  @Before
  public void setUp() throws Exception {
    this.setNow();
    this.setClientPlatformAsRandom();
    assertEquals(this.clientPlatform.getOwner(), this.operator);
  }

  @Test(expected = AssertionException.class)
  public void testCreateWithNull() throws Exception {
    this.resourceFileInternalService.create(null);
    fail();
  }

  @Test
  public void testCreateWithIllegalOwner() throws Exception {
    // Given
    final CreateResourceFileCmd cmd = ResourceFileUtils.createCmd(this.clientPlatform);
    Operator operator;
    do {
      operator = OperatorUtils.random(this.operatorRepository);
    } while (null != operator && cmd.getOwner() == operator.getId());
    cmd.setOwner(null == operator ? Randoms.positive() : operator.getId());

    // When
    OwnershipException e = (OwnershipException) Assertions
        .catchThrowable(() -> this.resourceFileInternalService.create(cmd));

    // Then
    Assertions.assertThat(e)
        .isInstanceOf(OwnershipException.class)
        .hasMessageContaining("not equal")
        .hasMessageContaining("[" + cmd.getOwner() + "]")
        .hasMessageMatching(format(".+\\[\\(%d, [^@]+@.+\\)\\].+", this.operator.getId()));
    assertEquals(e.toString(), operator.getId(), e.getExpected());
    assertEquals(e.toString(), this.clientPlatform.getOwner().getId(), e.getActual());
  }

  @Test
  public void testCreate() throws Exception {
    // Given
    final CreateResourceFileCmd cmd = ResourceFileUtils.createCmd(this.clientPlatform);

    // When
    final ResourceFile resourceFile = this.resourceFileInternalService.create(cmd);

    // Then
    assertNotNull(resourceFile);
    assertThat(resourceFile.getId(), greaterThan(0));
    assertEquals(this.operator, resourceFile.getOwner());
    assertEquals(this.clientPlatform, resourceFile.getClientPlatform());
    assertEquals(cmd.getName(), resourceFile.getName());
    assertThat(resourceFile.getCreate(), after(this.now));
    assertEquals(resourceFile.getCreate(), resourceFile.getUpdate());

    assertThat(resourceFile.getHistory(), empty());
    assertEquals(resourceFile.getCurrentRevisionNumber(), 0);
    assertNull(resourceFile.getCurrentRevision());
  }

  @Test
  public void testReadWith0() throws Exception {
    // When
    ResourceFile resourceFile = this.resourceFileInternalService.read(0);

    // Then
    assertNull(resourceFile);
  }

  @Test
  public void testReadWithId() throws Exception {
    // Given
    final ResourceFileEntity rf1 = ResourceFileUtils.create(this.clientPlatform, this.resourceFileInternalService);

    // When
    final ResourceFile rf2 = this.resourceFileInternalService.read(rf1.getId());

    // Then
    assertEquals(rf1, rf2);
  }

  @Test(expected = AssertionException.class)
  public void testReadWithNullCmd() throws Exception {
    this.resourceFileInternalService.read(null);
    fail();
  }

  @Test(expected = OwnershipException.class)
  public void testReadWithReadResourceFileCmdThatIllegalOwner() throws Exception {
    // Given
    final ResourceFileEntity rf1 = ResourceFileUtils.create(this.clientPlatform, this.resourceFileInternalService);
    int illegalOwner;
    do {
      illegalOwner = OperatorUtils.random(this.operatorRepository).getId();
    } while (rf1.getOwner().getId() == illegalOwner);
    ReadResourceFileCmd cmd = new ReadResourceFileCmd(illegalOwner, rf1.getId());

    // When
    this.resourceFileInternalService.read(cmd);

    // Then
    fail();
  }

  @Test
  public void testReadWithReadResourceFileCmd() throws Exception {
    // Given
    final ResourceFileEntity rf1 = ResourceFileUtils.create(this.clientPlatform, this.resourceFileInternalService);
    ReadResourceFileCmd cmd = new ReadResourceFileCmd(this.operator.getId(), rf1.getId());

    // When
    final ResourceFile rf2 = this.resourceFileInternalService.read(cmd);

    // Then
    assertEquals(rf1, rf2);
  }
}