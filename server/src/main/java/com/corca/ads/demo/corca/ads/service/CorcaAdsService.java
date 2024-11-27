package com.corca.ads.demo.corca.ads.service;

import com.corca.ads.demo.corca.ads.dto.CorcaAdsProductResponseDTO;

import java.util.concurrent.CompletableFuture;

public interface CorcaAdsService {

  CompletableFuture<CorcaAdsProductResponseDTO> fetchProductsFromCorcaAds(String placementId,
      String sessionId, String deviceId, String customerId, String userAgent);
}
