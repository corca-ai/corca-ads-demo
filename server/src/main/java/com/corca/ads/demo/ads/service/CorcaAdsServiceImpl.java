package com.corca.ads.demo.ads.service;

import com.corca.ads.demo.ads.dto.CorcaAdsProductResponseDTO;
import com.corca.ads.demo.ads.dto.CorcaAdsRequestDTO;
import com.corca.ads.demo.common.exception.CorcaAdsApiException;
import com.corca.ads.demo.product.service.ProductService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * CorcaAdsService 인터페이스의 구현체입니다. 이 서비스는 광고 상품을 가져오기 위해 Corca Ads API와 상호 작용하는 역할을 담당합니다.
 */
@Slf4j
@Service
public class CorcaAdsServiceImpl implements CorcaAdsService {

  private final String corcaAdsApiUrl = "https://api.adcio.ai";
  private final String corcaAdsClientId;
  private final RestTemplate restTemplate;
  private final ProductService productService;
  private final ObjectMapper objectMapper;

  /**
   * 지정된 매개변수로 새 CorcaAdsServiceImpl을 생성합니다.
   *
   * @param corcaAdsApiKey Corca Ads API에 접근하기 위한 API 키
   * @param corcaAdsClientId Corca Ads 서비스의 클라이언트 ID
   */
  public CorcaAdsServiceImpl(String corcaAdsApiKey, String corcaAdsClientId,
      RestTemplate restTemplate, ProductService productService, ObjectMapper objectMapper) {
    this.corcaAdsClientId = corcaAdsClientId;
    this.restTemplate = restTemplate;
    this.productService = productService;
    this.objectMapper = objectMapper;
  }

  /**
   * Web으로부터 받은 매개 변수를 기반으로 Corca Ads API에서 광고 상품을 가져옵니다.
   *
   * @param placementId 광고가 송출 될 지면의 ID
   * @param sessionId 엔드 유저의 세션 ID
   * @param deviceId 엔드 유저의 기기 ID
   * @param customerId 스토어에서 엔드 유저를 식별하기 위한 ID (스토어가 원하지 않거나, 로그인하지 않은 사용자의 경우 null일 수 있음)
   * @param userAgent 엔드 유저 브라우저의 User-Agent 헤더 정보
   * @return 가져온 광고 상품들과 지면 정보, 엔드 유저 이벤트 로그 수집을 위한 값을 반환 CorcaAdsProductResponseDTO
   * @throws CorcaAdsApiException 상품을 가져오는 동안 오류가 발생한 경우
   */
  @Async
  @Override
  public CompletableFuture<CorcaAdsProductResponseDTO> fetchProductsFromCorcaAds(String placementId,
      String sessionId, String deviceId, String customerId, String userAgent) {
    log.info("Fetching products from Corca Ads for placement: {}", placementId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    CorcaAdsRequestDTO request = CorcaAdsRequestDTO.create(corcaAdsClientId, placementId, sessionId,
        deviceId, customerId, userAgent);
    HttpEntity<CorcaAdsRequestDTO> entity = new HttpEntity<>(request, headers);
    String url = corcaAdsApiUrl + "/v1/advertisements/products";

    try {
      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

      log.info("Successfully fetched products from Corca Ads for placement: {}", placementId);

      CorcaAdsProductResponseDTO result = parseResponse(response.getBody());

      return CompletableFuture.completedFuture(result);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      log.error("HTTP error occurred while fetching products from Corca Ads", (Throwable) e);
      throw e;
    } catch (Exception e) {
      log.error("Error fetching products from Corca Ads", e);
      throw new CorcaAdsApiException("Failed to fetch products from Corca Ads", e);
    }
  }

  /**
   * Corca Ads API 응답을 파싱하여 CorcaAdsProductResponseDTO 객체로 변환합니다.
   *
   * @param responseBody Corca Ads API 응답 본문
   * @return CorcaAdsProductResponseDTO 객체
   * @throws Exception 응답을 파싱하는 동안 오류가 발생한 경우
   */
  private CorcaAdsProductResponseDTO parseResponse(String responseBody) throws Exception {
    JsonNode root = objectMapper.readTree(responseBody);
    CorcaAdsProductResponseDTO productResponse = new CorcaAdsProductResponseDTO();

    List<CorcaAdsProductResponseDTO.SuggestionDTO> suggestions = new ArrayList<>();

    for (JsonNode suggestionNode : root.path("suggestions")) {
      suggestions.add(parseSuggestion(suggestionNode));
    }

    productResponse.setSuggestions(suggestions);
    productResponse.setPlacement(parsePlacement(root.path("placement")));

    return productResponse;
  }

  /**
   * Corca Ads API 응답에서 제안된 광고 상품 정보를 파싱하여 CorcaAdsProductResponseDTO.SuggestionDTO 객체로 변환합니다.
   * 
   * API에서는 상품의 ID만을 제공하므로, 필요한 상품 정보를 추가로 가져와야 합니다.
   *
   * @param suggestionNode 제안된 상품 정보를 포함하는 JSON 노드
   * @return CorcaAdsProductResponseDTO.SuggestionDTO 객체
   */
  private CorcaAdsProductResponseDTO.SuggestionDTO parseSuggestion(JsonNode suggestionNode) {
    CorcaAdsProductResponseDTO.SuggestionDTO suggestionDTO =
        new CorcaAdsProductResponseDTO.SuggestionDTO();
    // TODO: idOnStore에서 api 응답 스키마 변경 후 id로 변경
    String productId = suggestionNode.path("product").path("idOnStore").asText();

    suggestionDTO.setProduct(productService.getProduct(productId));
    suggestionDTO.setLogOptions(parseLogOptions(suggestionNode.path("logOptions")));

    return suggestionDTO;
  }

  private CorcaAdsProductResponseDTO.LogOptionsDTO parseLogOptions(JsonNode logOptionsNode) {
    CorcaAdsProductResponseDTO.LogOptionsDTO logOptionsDTO =
        new CorcaAdsProductResponseDTO.LogOptionsDTO();

    logOptionsDTO.setRequestId(logOptionsNode.path("requestId").asText());
    logOptionsDTO.setAdsetId(logOptionsNode.path("adsetId").asText());

    return logOptionsDTO;
  }

  private CorcaAdsProductResponseDTO.PlacementDTO parsePlacement(JsonNode placementNode) {
    CorcaAdsProductResponseDTO.PlacementDTO placementDTO =
        new CorcaAdsProductResponseDTO.PlacementDTO();

    placementDTO.setId(placementNode.path("id").asText());
    placementDTO.setTitle(placementNode.path("title").asText());
    placementDTO.setDisplayCount(placementNode.path("displayCount").asInt());
    placementDTO.setActivated(placementNode.path("activated").asBoolean());

    return placementDTO;
  }
}
