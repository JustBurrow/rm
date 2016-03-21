/**
 *
 */
package kr.lul.urs.core.service.internal;

import static kr.lul.urs.application.configuration.InjectionConstants.Beans.NAME_TRANSACTION_MANAGER;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import org.springframework.transaction.annotation.Transactional;

import kr.lul.urs.core.command.CreateOperatorCmd;
import kr.lul.urs.core.domain.Operator;

/**
 * 프로덕트 운영자를 다루는 내부 로직.
 *
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 3. 21.
 */
@Transactional(transactionManager = NAME_TRANSACTION_MANAGER, propagation = MANDATORY)
public interface OperatorInternalService {
  /**
   * @param cmd
   * @return
   */
  public Operator create(CreateOperatorCmd cmd);

  /**
   * @param email
   * @return
   */
  public Operator readByEmail(String email);
}
