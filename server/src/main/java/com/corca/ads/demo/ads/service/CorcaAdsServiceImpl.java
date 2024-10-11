package com.corca.ads.demo.ads.service;

import com.corca.ads.demo.ads.dto.CorcaAdsRequestDTO;
import com.corca.ads.demo.ads.dto.CorcaAdsProductResponseDTO;
import com.corca.ads.demo.common.exception.CorcaAdsApiException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class CorcaAdsServiceImpl implements CorcaAdsService {

  private static final Logger logger = LoggerFactory.getLogger(CorcaAdsServiceImpl.class);

  private final String corcaAdsApiUrl = "https://api.adcio.ai";
  private final String corcaAdsApiKey;
  private final String corcaAdsClientId;
  private final RestTemplate restTemplate;

  public CorcaAdsServiceImpl(
      String corcaAdsApiKey,
      String corcaAdsClientId,
      RestTemplate restTemplate) {
    this.corcaAdsApiKey = corcaAdsApiKey;
    this.corcaAdsClientId = corcaAdsClientId;
    this.restTemplate = restTemplate;
  }

  @Override
  public CorcaAdsProductResponseDTO fetchProductsFromCorcaAds(
      String placementId,
      String sessionId,
      String deviceId,
      String customerId,
      String userAgent) {
    logger.info("Fetching products from Corca Ads for placement: {}, placementId");

    HttpHeaders headers = createHeaders();
    CorcaAdsRequestDTO request = CorcaAdsRequestDTO.create(
        corcaAdsClientId,
        placementId,
        sessionId,
        deviceId,
        customerId,
        userAgent);
    HttpEntity<CorcaAdsRequestDTO> entity = new HttpEntity<>(request, headers);

    String url = corcaAdsApiUrl + "/v1/advertisements/products";

    try {
      ResponseEntity<CorcaAdsProductResponseDTO> response = restTemplate.exchange(
          url,
          HttpMethod.POST,
          entity,
          CorcaAdsProductResponseDTO.class);
      logger.info("Successfully fetched products from Corca Ads for placement: {}", placementId);

      // local file로 보리보리 상품 데이터를 더미로 넣어두고 해당 파일에서 id를 맵핑하여 반환하도록 할 예정 -> 이 로직은 보리보리 측 DB 조회를 보여주기 위함이라는 것을 주석으로 명시

      return response.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("HTTP error occurred while fetching products from Corca Ads", (Throwable) e);
      throw e;
    } catch (Exception e) {
      logger.error("Error fetching products from Corca Ads", e);
      throw new CorcaAdsApiException("Failed to fetch products from Corca Ads", e);
    }
  }

  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-api-key", corcaAdsApiKey);
    return headers;
  }
}
