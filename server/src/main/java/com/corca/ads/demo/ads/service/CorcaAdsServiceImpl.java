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

/**
 * CorcaAdsService 인터페이스의 구현체입니다. 이 서비스는 광고 상품을 가져오기 위해 Corca Ads API와 상호 작용하는 역할을 담당합니다.
 */
@Service
public class CorcaAdsServiceImpl implements CorcaAdsService {

  private static final Logger logger = LoggerFactory.getLogger(CorcaAdsServiceImpl.class);

  private final String corcaAdsApiUrl = "https://api.adcio.ai";
  private final String corcaAdsApiKey;
  private final String corcaAdsClientId;
  private final RestTemplate restTemplate;

  /**
   * 지정된 매개변수로 새 CorcaAdsServiceImpl을 생성합니다.
   *
   * @param corcaAdsApiKey Corca Ads API에 접근하기 위한 API 키
   * @param corcaAdsClientId Corca Ads 서비스의 클라이언트 ID
   */
  public CorcaAdsServiceImpl(String corcaAdsApiKey, String corcaAdsClientId,
      RestTemplate restTemplate) {
    this.corcaAdsApiKey = corcaAdsApiKey;
    this.corcaAdsClientId = corcaAdsClientId;
    this.restTemplate = restTemplate;
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
  @Override
  public CorcaAdsProductResponseDTO fetchProductsFromCorcaAds(String placementId, String sessionId,
      String deviceId, String customerId, String userAgent) {
    logger.info("Fetching products from Corca Ads for placement: {}, placementId");

    HttpHeaders headers = createHeaders();
    CorcaAdsRequestDTO request = CorcaAdsRequestDTO.create(corcaAdsClientId, placementId, sessionId,
        deviceId, customerId, userAgent);
    HttpEntity<CorcaAdsRequestDTO> entity = new HttpEntity<>(request, headers);

    String url = corcaAdsApiUrl + "/v1/advertisements/products";

    try {
      ResponseEntity<CorcaAdsProductResponseDTO> response =
          restTemplate.exchange(url, HttpMethod.POST, entity, CorcaAdsProductResponseDTO.class);
      logger.info("Successfully fetched products from Corca Ads for placement: {}", placementId);

      // todo: local file로 보리보리 상품 데이터를 더미로 넣어두고 해당 파일에서 id를 맵핑하여 반환하도록 할 예정 -> 이 로직은 보리보리 측 DB 조회를
      // 보여주기 위함이라는 것을 주석으로 명시
      // todo: 해당 엔드포인트 및 서비스구현체에서 보리보리 상품 DTO로 변환하여 반환, JsonUtil 사용

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
