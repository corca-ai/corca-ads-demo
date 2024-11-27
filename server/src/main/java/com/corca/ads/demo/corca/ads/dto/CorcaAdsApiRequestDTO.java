package com.corca.ads.demo.corca.ads.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Corca Ads API 요청 DTO")
public class CorcaAdsApiRequestDTO extends BaseCorcaAdsRequestDTO {
  @Schema(description = "Corca Ads 서비스의 클라이언트 ID", required = true)
  private String clientId;

  public static CorcaAdsApiRequestDTO create(String clientId, String placementId, String sessionId,
      String deviceId, String customerId, String userAgent) {
    CorcaAdsApiRequestDTO request = new CorcaAdsApiRequestDTO();
    request.setClientId(clientId);
    request.setPlacementId(placementId);
    request.setSessionId(sessionId);
    request.setDeviceId(deviceId);
    request.setCustomerId(customerId);
    request.setUserAgent(userAgent);
    return request;
  }
}
