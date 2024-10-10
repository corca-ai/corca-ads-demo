package com.corca.ads.demo.ads.service;

import com.corca.ads.demo.ads.dto.CorcaAdsProductResponseDTO;

public interface CorcaAdsService {
  CorcaAdsProductResponseDTO fetchProductsFromCorcaAds(
      String placementId,
      String sessionId,
      String deviceId,
      String customerId,
      String userAgent);
}
