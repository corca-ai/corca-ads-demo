package com.corca.ads.demo.ads.controller;

import com.corca.ads.demo.ads.dto.CorcaAdsProductResponseDTO;
import com.corca.ads.demo.ads.dto.CorcaAdsProductRequestDTO;
import com.corca.ads.demo.ads.service.CorcaAdsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Corca Ads 관련 API 요청을 처리하는 컨트롤러입니다. 이 컨트롤러는 광고 상품을 가져오기 위한 엔드포인트를 제공합니다.
 */
@RestController
@Tag(name = "Corca Ads", description = "Corca Ads에서 광고 상품을 가져오는 데 사용되는 API")
@RequestMapping("/api/corca-ads")
public class CorcaAdsController {

  private final CorcaAdsService corcaAdsService;

  public CorcaAdsController(CorcaAdsService corcaAdsService) {
    this.corcaAdsService = corcaAdsService;
  }

  /**
   * 제공된 매개변수를 기반으로 Corca Ads에서 상품 광고를 가져옵니다.
   * 
   * 📍 참고: 데모에서는 실제 DB 대신 더미 JSON 파일에서 상품 데이터를 조회하여 맵핑합니다.
   *
   * @param params placementId, sessionId, deviceId, customerId, userAgent를 포함한 요청 매개변수 맵
   * @return 가져온 상품 광고가 포함된 CorcaAdsProductResponseDTO를 담은 ResponseEntity
   */
  @GetMapping("/products")
  @Operation(summary = "Corca Ads를 통해 광고 상품을 가져옵니다.",
      description = "제공된 매개변수에 따라 제안된 광고 상품을 반환합니다.")
  @ApiResponse(responseCode = "200", description = "Success",
      content = @Content(schema = @Schema(implementation = CorcaAdsProductResponseDTO.class)))
  public CompletableFuture<ResponseEntity<CorcaAdsProductResponseDTO>> getCorcaAdsProducts(
      @Parameter(description = "Corca Ads 요청 파라미터", schema = @Schema(
          implementation = CorcaAdsProductRequestDTO.class)) @RequestParam Map<String, String> params) {
    String placementId = params.get("placementId");
    String sessionId = params.get("sessionId");
    String deviceId = params.get("deviceId");
    String customerId = params.get("customerId");
    String userAgent = params.get("userAgent");

    return corcaAdsService
        .fetchProductsFromCorcaAds(placementId, sessionId, deviceId, customerId, userAgent)
        .thenApply(ResponseEntity::ok);
  }
}
