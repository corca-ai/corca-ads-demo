package com.corca.ads.demo.ads.dto;

import com.corca.ads.demo.product.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Corca Ads 상품 응답 DTO")
public class CorcaAdsProductResponseDTO {
  @Schema(description = "광고 상품 목록")
  private List<SuggestionDTO> suggestions = new ArrayList<>();

  @Schema(description = "광고 지면 정보")
  private PlacementDTO placement;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Schema(description = "제안된 광고 상품 정보")
  public static class SuggestionDTO {
    @Schema(description = "상품 정보")
    private ProductDTO product;

    @Schema(description = "성과 식별자. 광고 제안에 대한 성과 추적을 위한 정보")
    private LogOptionsDTO logOptions;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Schema(description = "성과 식별자. 광고 제안에 대한 성과 추적을 위한 정보")
  public static class LogOptionsDTO {
    @Schema(description = "광고에 대한 요청을 식별하기 위한 값, 엔드유저 이벤트에 대해 로그 수집 시 사용")
    private String requestId;

    @Schema(description = "광고 요청으로 나간 상품의 식별자. 광고의 소재를 식별하기 위한 값")
    private String adsetId;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Schema(description = "광고 지면(구좌) 정보")
  public static class PlacementDTO {
    @Schema(description = "Corca Ads 어드민에서 생성한 지면의 ID")
    private String id;

    @Schema(description = "지면을 구분하기 위한 이름")
    private String title;

    @Schema(description = "지면에 나갈 (광고) 상품의 개수. 이 숫자만큼 광고 상품이 반환됩니다.")
    private int displayCount;

    @Schema(description = "지면 활성화 여부")
    private boolean activated;
  }
}
