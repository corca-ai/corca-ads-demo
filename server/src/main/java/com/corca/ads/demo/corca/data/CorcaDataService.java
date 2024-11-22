package com.corca.ads.demo.corca.data;

import com.corca.ads.demo.product.dto.ProductDTO;
import com.corca.ads.demo.common.exception.CorcaApiException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.List;
import java.util.Map;

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

  private Object convertToRequest(ProductDTO product) {
    // [GET] /v1/products/product-fields 으로 확인하여, 등록된 스키마와 동일한 형태로 보내줘야함
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
