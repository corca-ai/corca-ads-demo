package com.corca.ads.demo.ads.dto;

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
  private PlacementDTO placement;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SuggestionDTO {
    private ProductDTO product;

    @Schema(description = "광고 제안에 대한 성과 추적을 위한 값, 엔드유저 이벤트에 대해 로그 수집 시 사용")
    private LogOptionsDTO logOptions;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ProductDTO {
    @Schema(description = "스토어의 데이터베이스에 저장된 상품의 고유 식별자")
    private String id;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LogOptionsDTO {
    private String requestId;
    private String adsetId;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PlacementDTO {
    private String id;
    private String title;

    @Schema(description = "지면(구좌)에 제안 받을 광고 아이템의 개수")
    private int displayCount;

    private boolean activated;
  }
}
