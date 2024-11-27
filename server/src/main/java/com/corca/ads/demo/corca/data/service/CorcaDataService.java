package com.corca.ads.demo.corca.data.service;

import com.corca.ads.demo.product.dto.ProductDTO;
import com.corca.ads.demo.common.exception.CorcaApiException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/*
 * Corca Data API와 상호 작용하는 역할을 담당합니다.
 * 
 * 이 서비스는 스토어에서 관리하는 각 셀러의 상품 데이터를 Corca Data 서버와 연동하여, 어드민에서 셀러들이 광고를 운영하거나 지면에 광고가 송출될 수 있게 합니다.
 */
@Slf4j
@Component
public class CorcaDataService {

  private final String corcaDataApiUrl = "https://data.corca.dev/v1/integrations";
  private final String corcaDataApiKeyId;
  private final String corcaDataApiKeySecret;
  private final RestTemplate restTemplate;

  public CorcaDataService(String corcaDataApiKeyId, String corcaDataApiKeySecret,
      RestTemplate restTemplate) {
    this.corcaDataApiKeyId = corcaDataApiKeyId;
    this.corcaDataApiKeySecret = corcaDataApiKeySecret;
    this.restTemplate = restTemplate;
  }

  /*
   * Corca Data API에서 제공하는 상품 연동을 위한 엔드포인트는 다음과 같이 구성됩니다.
   * 
   * @formatter:off
   * - 상품 등록: POST /products/create
   * - 상품 일괄 등록: POST /products/bulk-create
   * - 상품 수정: POST /products/update
   * - 상품 일괄 수정: POST /products/bulk-update
   * - 상품 삭제: POST /products/delete
   * - 상품 복원: POST /products/restore
   * @formatter:on
   * 
   * 각 엔드포인트의 보다 자세한 설명은 아래 주소를 통해 확인하실 수 있습니다.
   * https://corcaai.notion.site/API-137dd8f2aea280b2a64bea0581246e84
  */

  public void registerProduct(ProductDTO product) {
    executeRequest("/products/create", Map.of("product", convertToRequest(product)),
        "Register product: " + product.getId());
  }

  public void registerProducts(List<ProductDTO> products) {
    executeRequest("/products/bulk-create",
        Map.of("products", products.stream().map(this::convertToRequest).toList()),
        String.format("Register %d products", products.size()));
  }

  public void updateProduct(ProductDTO product) {
    executeRequest("/products/update", Map.of("product", convertToRequest(product)),
        "Update product: " + product.getId());
  }

  public void updateProducts(List<ProductDTO> products) {
    executeRequest("/products/bulk-update",
        Map.of("products", products.stream().map(this::convertToRequest).toList()),
        String.format("Update %d products", products.size()));
  }

  public void deleteProduct(String productId) {
    executeRequest("/products/delete", Map.of("productId", String.valueOf(productId)),
        "Delete product: " + productId);
  }

  public void restoreProduct(String productId) {
    executeRequest("/products/restore", Map.of("productId", String.valueOf(productId)),
        "Restore product: " + productId);
  }

  /*
   * Corca Data API에 요청을 보내기 위한 헤더를 생성합니다.
   * 
   * 인증 정보는 발급해드리는 API Key ID와 API Key Secret을 {api-key-id}:{api-key-secret} 형태의 값을 Base64로 인코딩하여,
   * Authorization 헤더에 Basic {base64-encoded-credentials} 형태로 전달해야 합니다.
   */
  private HttpHeaders createHeaders() {
    String credentials = corcaDataApiKeyId + ":" + corcaDataApiKeySecret;
    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Basic " + encodedCredentials);

    return headers;
  }

  private void executeRequest(String path, Object body, String operationDesc) {
    try {
      log.info("Starting: {}", operationDesc);

      HttpHeaders headers = createHeaders();
      HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

      ResponseEntity<Void> response = restTemplate.exchange(this.corcaDataApiUrl + path,
          HttpMethod.POST, requestEntity, Void.class);

      if (response.getStatusCode() == HttpStatus.CREATED) { // 201
        log.info("Successfully completed: {}", operationDesc);
      }
    } catch (Exception e) {
      log.error("Failed to execute: {}", operationDesc, e);
      throw new CorcaApiException("Failed to execute: " + operationDesc, e);
    }
  }

  /*
   * ⚠️ 상품을 등록하거나 수정할 때는 [GET] /v1/products/product-fields 엔드포인트를 통해 확인한 스키마와 동일한 형태로 요청해야 합니다.
   */
  private Object convertToRequest(ProductDTO product) {
    // @formatter:off
    return Map.of(
      "id", product.getId(),
      "name", product.getName(),
      "price", product.getPrice(),
      "image_url", product.getImage(),
      "summary", product.getSummary(),
      "seller_id", "demo-seller-1"
    );
    // @formatter:on
  }
}
