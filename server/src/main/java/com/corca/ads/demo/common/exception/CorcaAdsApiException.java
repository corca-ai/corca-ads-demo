package com.corca.ads.demo.common.exception;

public class CorcaAdsApiException extends RuntimeException {
  public CorcaAdsApiException(String message) {
    super(message);
  }

  public CorcaAdsApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
