package com.corca.ads.demo.common.exception;

public class CorcaApiException extends RuntimeException {

  public CorcaApiException(String message) {
    super(message);
  }

  public CorcaApiException(String message, Throwable cause) {
    super(message, cause);
  }
}

