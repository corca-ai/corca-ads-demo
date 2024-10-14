package com.corca.ads.demo.ads.web;

import com.corca.ads.demo.ads.service.CorcaAdsService;
import com.corca.ads.demo.ads.dto.CorcaAdsRequestDTO;
import com.corca.ads.demo.ads.dto.CorcaAdsProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Corca Ads 관련 API 요청을 처리하는 컨트롤러입니다. 이 컨트롤러는 광고 상품을 가져오기 위한 엔드포인트를 제공합니다.
 */
@RestController
@Tag(name = "Corca Ads", description = "API used to fetch products from Corca Ads")
@RequestMapping("/api/corca-ads")
public class CorcaAdsController {

  private final CorcaAdsService corcaAdsService;

  public CorcaAdsController(CorcaAdsService corcaAdsService) {
    this.corcaAdsService = corcaAdsService;
  }

  /**
   * 제공된 매개변수를 기반으로 Corca Ads에서 상품 광고를 가져옵니다.
   *
   * @param params placementId, sessionId, deviceId, customerId, userAgent를 포함한 요청 매개변수 맵
   * @return 가져온 상품 광고가 포함된 CorcaAdsProductResponseDTO를 담은 ResponseEntity
   */
  @GetMapping("/products")
  @Operation(summary = "Fetch products from Corca Ads",
      description = "Returns retrieved advertisement products based on the parameters provided.")
  @ApiResponse(responseCode = "200", description = "Success",
      content = @Content(schema = @Schema(implementation = CorcaAdsProductResponseDTO.class)))
  public ResponseEntity<CorcaAdsProductResponseDTO> getCorcaAdsProducts(
      @Parameter(description = "Corca Ads request parameters", schema = @Schema(
          implementation = CorcaAdsRequestDTO.class)) @RequestParam Map<String, String> params) {
    String placementId = params.get("placementId");
    String sessionId = params.get("sessionId");
    String deviceId = params.get("deviceId");
    String customerId = params.get("customerId");
    String userAgent = params.get("userAgent");

    CorcaAdsProductResponseDTO result = corcaAdsService.fetchProductsFromCorcaAds(placementId,
        sessionId, deviceId, customerId, userAgent);
    return ResponseEntity.ok(result);
  }
}
