/**
 *
 */
package kr.lul.urs.core;

import static kr.lul.urs.util.Asserts.notNull;
import static kr.lul.urs.util.Asserts.positive;
import static kr.lul.urs.util.Randoms.in;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.util.Collections;
import java.util.List;

import kr.lul.urs.application.web.request.CreateAgentPlatformReq;
import kr.lul.urs.core.command.CreateClientPlatformCmd;
import kr.lul.urs.core.command.ReadClientPlatformCmd;
import kr.lul.urs.core.domain.ClientPlatform;
import kr.lul.urs.core.domain.entity.ClientPlatformEntity;
import kr.lul.urs.core.dto.ClientPlatformDto;
import kr.lul.urs.core.dto.OperatorDto;
import kr.lul.urs.core.repository.ClientPlatformRepository;
import kr.lul.urs.core.service.ClientPlatformService;

/**
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 5. 3.
 */
public abstract class ClientPlatformApiUtils {
  /**
   * @return
   * @since 2016. 6. 7.
   */
  public static CreateAgentPlatformReq createReq() {
    String code = randomAlphabetic(in(1, 3)).toLowerCase() + randomAlphanumeric(in(0, 10));
    String label = "Test%" + randomAlphanumeric(in(1, 10));
    String description = "Random Agent Platform for test.";

    CreateAgentPlatformReq req = new CreateAgentPlatformReq(code, label, description);

    return req;
  }

  /**
   * 임의의 클라이언트 플랫폼을 만들 수 있는 커맨드를 만든다.
   *
   * @param owner
   * @return
   * @since 2016. 4. 28.
   */
  public static CreateClientPlatformCmd createCmd(int owner) {
    String code = randomAlphabetic(in(1, 3)).toLowerCase() + randomAlphanumeric(in(0, 10));
    String label = "Test%" + randomAlphanumeric(in(1, 10));
    String description = "Random Client Platform for test.";
    return new CreateClientPlatformCmd(owner, code, label, description);
  }

  /**
   * 임의의 클라이언트 플랫폼을 만들 수 있는 커맨드를 만든다.
   *
   * @param owner
   * @return
   */
  public static CreateClientPlatformCmd createCmd(OperatorDto owner) {
    notNull(owner);

    return createCmd(owner.getId());
  }

  /**
   * @param id
   * @param owner
   * @return
   * @since 2016. 5. 5.
   */
  public static ReadClientPlatformCmd readCmd(int id, int owner) {
    ReadClientPlatformCmd cmd = new ReadClientPlatformCmd();
    cmd.setId(id);
    cmd.setOwner(owner);
    return cmd;
  }

  /**
   * 임의의 읽기 커맨드 오브젝트를 만든다.
   *
   * @param clientPlatformRepository
   * @return
   * @since 2016. 5. 5.
   */
  public static ReadClientPlatformCmd readCmd(ClientPlatformRepository repository) {
    notNull(repository);

    List<ClientPlatformEntity> list = repository.findAll();
    Collections.shuffle(list);
    ClientPlatform clientPlatform = list.get(0);

    ReadClientPlatformCmd cmd = new ReadClientPlatformCmd();
    cmd.setId(clientPlatform.getId());
    cmd.setOwner(clientPlatform.getOwner().getId());
    return cmd;
  }

  /**
   * @param clientPlatformService
   * @return
   * @since 2016. 5. 5.
   */
  public static ReadClientPlatformCmd readCmd(ClientPlatformService clientPlatformService) {
    notNull(clientPlatformService);

    List<ClientPlatformDto> list = clientPlatformService.list().value();
    Collections.shuffle(list);
    ClientPlatformDto clientPlatform = list.get(0);

    ReadClientPlatformCmd cmd = new ReadClientPlatformCmd();
    cmd.setId(clientPlatform.getId());
    cmd.setOwner(clientPlatform.getOwner());
    return cmd;
  }

  /**
   * @param owner
   * @param clientPlatformService
   * @return
   * @since 2016. 5. 16.
   */
  public static ClientPlatformDto create(OperatorDto owner, ClientPlatformService clientPlatformService) {
    notNull(owner);
    positive(owner.getId());
    notNull(clientPlatformService);

    return clientPlatformService.create(createCmd(owner)).value();
  }

  /**
   * @param owner
   * @param clientPlatformService
   * @return
   * @since 2016. 5. 17.
   */
  public static ClientPlatformDto create(int owner, ClientPlatformService clientPlatformService) {
    positive(owner);
    notNull(clientPlatformService);

    return clientPlatformService.create(createCmd(owner)).value();
  }

  protected ClientPlatformApiUtils() {
    throw new UnsupportedOperationException();
  }
}
