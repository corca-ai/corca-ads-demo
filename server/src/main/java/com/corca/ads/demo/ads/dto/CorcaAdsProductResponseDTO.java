package com.corca.ads.demo.ads.dto;

import com.corca.ads.demo.product.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Corca Ads 상품 응답 DTO")
public class CorcaAdsProductResponseDTO {
  @Schema(description = "광고 제안 목록")
  private List<SuggestionDTO> suggestions;

  @Schema(description = "광고 지면 정보")
  private PlacementDTO placement;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Schema(description = "광고 제안 정보")
  public static class SuggestionDTO {
    @Schema(description = "제안된 광고 상품")
    private ProductDTO product;

    @Schema(description = "광고 제안에 대한 성과 추적을 위한 정보")
    private LogOptionsDTO logOptions;
  }


  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Schema(description = "광고 성과 추적 정보")
  public static class LogOptionsDTO {
    @Schema(description = "광고에 대한 요청을 식별하기 위한 값, 엔드유저 이벤트에 대해 로그 수집 시 사용")
    private String requestId;

    @Schema(description = "광고의 소재를 식별하기 위한 값")
    private String adsetId;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Schema(description = "광고 지면 정보")
  public static class PlacementDTO {
    @Schema(description = "지면 ID")
    private String id;

    @Schema(description = "지면 타이틀")
    private String title;

    @Schema(description = "지면(구좌)에 제안 받을 광고 아이템의 개수")
    private int displayCount;

    @Schema(description = "지면 활성화 여부")
    private boolean activated;
  }
}
