package kr.lul.urs.util.converter;

import static kr.lul.urs.util.converter.InstantToUtcMillisConverter.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Instant;

import org.junit.Test;

import kr.lul.urs.AbstractTest;

public class InstantToUtcMillisConverterTest extends AbstractTest {
  @Test
  public void testConvertWithNull() throws Exception {
    assertNull(INSTANCE.convert(null));
  }

  @Test
  public void testConvert() throws Exception {
    Instant now = Instant.now();

    Long utc = INSTANCE.convert(now);

    assertNotNull(utc);
    assertEquals(now.toEpochMilli(), utc.longValue());
  }
}
