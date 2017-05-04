package com.deppon.foss.module.pickup.predeliver.api.shared.util;

import org.apache.commons.codec.binary.Base64;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CookieToken;

public final class TokenMarshal
{
  public static String marshal(CookieToken token)
  {
    return new String(Base64.encodeBase64String(token.toBytes()));
  }

  public static CookieToken unMarshal(String tokenStr)
  {
    return new CookieToken(Base64.decodeBase64(tokenStr));
  }
}