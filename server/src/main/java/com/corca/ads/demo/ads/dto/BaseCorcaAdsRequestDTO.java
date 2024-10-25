package com.corca.ads.demo.ads.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseCorcaAdsRequestDTO {
  @Schema(description = "엔드유저가 스토어를 방문하고 있는 동안 유지되는 세션의 고유 식별자", required = true)
  private String sessionId;

  @Schema(description = "엔드유저의 디바이스 고유 식별자", required = true)
  private String deviceId;

  @Schema(description = "스토어에 로그인한 엔드유저를 식별하기 위한 값, 로그인 하지 않은 유저는 null로 설정 (optional)", required = false)
  private String customerId;

  @Schema(description = "Corca Ads 서비스에서 발급받은 광고가 나갈 지면(구좌) ID", required = true)
  private String placementId;

  @Schema(description = "엔드유저의 User-Agent 정보 (optional)", required = false)
  private String userAgent;
}
