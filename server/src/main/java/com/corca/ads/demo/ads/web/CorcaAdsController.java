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

@RestController
@Tag(name = "Corca Ads", description = "API used to fetch products from Corca Ads")
@RequestMapping("/api/corca-ads")
public class CorcaAdsController {

  private final CorcaAdsService corcaAdsService;

  public CorcaAdsController(CorcaAdsService corcaAdsService) {
    this.corcaAdsService = corcaAdsService;
  }

  @GetMapping("/products")
  @Operation(summary = "Fetch products from Corca Ads", description = "Returns retrieved advertisement products based on the parameters provided.")
  @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = CorcaAdsProductResponseDTO.class)))
  public ResponseEntity<CorcaAdsProductResponseDTO> getCorcaAdsProducts(
      @Parameter(description = "Corca Ads request parameters", schema = @Schema(implementation = CorcaAdsRequestDTO.class)) @RequestParam Map<String, String> params) {
    String placementId = params.get("placementId");
    String sessionId = params.get("sessionId");
    String deviceId = params.get("deviceId");
    String customerId = params.get("customerId");
    String userAgent = params.get("userAgent");

    CorcaAdsProductResponseDTO result = corcaAdsService.fetchProductsFromCorcaAds(
        placementId,
        sessionId,
        deviceId,
        customerId,
        userAgent);
    return ResponseEntity.ok(result);
  }
}