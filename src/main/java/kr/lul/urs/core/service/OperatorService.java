/**
 *
 */
package kr.lul.urs.core.service;

import static kr.lul.urs.application.configuration.InjectionConstants.Beans.NAME_TRANSACTION_MANAGER;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 프로덕트 운영자를 다루는 로직.
 *
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 3. 21.
 */
@Transactional(transactionManager = NAME_TRANSACTION_MANAGER, propagation = REQUIRES_NEW)
public interface OperatorService extends UserDetailsService {
}