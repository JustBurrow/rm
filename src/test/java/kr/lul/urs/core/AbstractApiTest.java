/**
 *
 */
package kr.lul.urs.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import kr.lul.urs.AbstractTest;
import kr.lul.urs.core.dto.ClientPlatformDto;
import kr.lul.urs.core.dto.OperatorDto;
import kr.lul.urs.core.service.ClientPlatformService;
import kr.lul.urs.core.service.OperatorService;

/**
 * 도메인 엔티티에 직접 접근하지 않고, DTO 혹은 ID를 기준으로 로직을 실행해 내부적으로만 도메인 엔티티에 접근하는 테스트용.
 * 다음의 레이어에서 주로 사용한다.
 * <ul>
 * <li>{@link kr.lul.urs.core.dto}</li>
 * <li>{@link kr.lul.urs.core.service}</li>
 * </ul>
 *
 * @since 2016. 5. 16.
 * @author Just Burrow just.burrow@lul.kr
 */
public abstract class AbstractApiTest extends AbstractTest {
  @Autowired
  protected OperatorService       operatorService;
  @Autowired
  protected ClientPlatformService clientPlatformService;

  protected OperatorDto           operator;
  protected ClientPlatformDto     clientPlatform;

  /**
   * @since 2016. 5. 16.
   */
  protected void setOperatorAsRandom() {
    if (null == this.now) {
      this.setNow();
    }

    this.operator = OperatorApiUtils.create(this.operatorService);
  }

  /**
   * @since 2016. 5. 16.
   */
  protected void setClientPlatformAsRandom() {
    if (null == this.operator) {
      this.setOperatorAsRandom();
    }

    this.clientPlatform = ClientPlatformApiUtils.create(this.operator, this.clientPlatformService);

    assertThat(this.clientPlatform).isNotNull();
    assertThat(this.clientPlatform.getId()).isGreaterThan(0);
    assertThat(this.clientPlatform.getOwner()).isEqualTo(this.operator.getId());
    this.assertTimestamp(this.clientPlatform);
  }
}