/**
 *
 */
package kr.lul.urs.core.dao;

import static kr.lul.urs.util.Asserts.assignable;
import static kr.lul.urs.util.Asserts.notNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.lul.urs.core.domain.ClientPlatform;
import kr.lul.urs.core.domain.entity.ClientPlatformEntity;
import kr.lul.urs.core.repository.ClientPlatformRepository;

/**
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 4. 8.
 */
@Service
class ClientPlatformDaoImpl implements ClientPlatformDao {
  @Autowired
  private ClientPlatformRepository clientPlatformRepository;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>ClientPlatformDao
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public ClientPlatform insert(ClientPlatform clientPlatform) {
    notNull(clientPlatform);
    assignable(clientPlatform, ClientPlatformEntity.class);

    clientPlatform = this.clientPlatformRepository.save((ClientPlatformEntity) clientPlatform);

    return clientPlatform;
  }
}