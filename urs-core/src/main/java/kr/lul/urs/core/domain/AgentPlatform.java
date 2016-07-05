/**
 *
 */
package kr.lul.urs.core.domain;

import kr.lul.urs.spring.jpa.ownership.Ownable;
import kr.lul.urs.spring.jpa.timestamp.Updatable;

/**
 * 프로덕트의 에이전트(클라이언트)가 지원하는 플랫폼 정보.
 * 에이전트는 각 플랫폼마다 서로 다른 구현을 가지며, 그에 필요한 리소스 파일이 서로 다르다.
 *
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 4. 4.
 */
public interface AgentPlatform extends Ownable<Operator>, Updatable {
  /**
   * @return ID
   */
  public int getId();

  /**
   * URI 등에서 플랫폼 정보를 표시할 때 사용하는 코드.
   *
   * @return 플랫폼 코드.
   */
  public String getCode();

  /**
   * 관리도구 등에서 관리자가 읽기 편하도록 사용하는 이름.
   *
   * @return 관리용 이름.
   */
  public String getLabel();

  /**
   * @param label
   *          관리용 이름.
   */
  public void setLabel(String label);

  /**
   * @return 플랫폼 설명.
   */
  public String getDescription();

  /**
   * @param description
   *          플랫폼 설명.
   */
  public void setDescription(String description);
}