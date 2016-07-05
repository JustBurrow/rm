/**
 *
 */
package kr.lul.urs.util;

import java.time.Instant;

/**
 * @author Just Burrow just.burrow@lul.kr
 * @since 2016. 5. 9.
 */
public class SystemTimeProvider implements TimeProvider {
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // <I>TimeProvider
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Instant now() {
    return Instant.now();
  }
}