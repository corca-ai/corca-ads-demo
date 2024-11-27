package com.corca.ads.demo.product.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.corca.ads.demo.corca.data.service.CorcaDataService;

/*
 * Corca Data API 연동을 위한 이벤트 리스너입니다.
 *
 * 상품 데이터 변경 시 발생하는 이벤트를 비동기로 처리하고, 이를 통해 스토어의 기존 상품 관리 로직과 Corca Data API 연동을 분리 할 수 있습니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {
  private final CorcaDataService corcaDataService;

  @Async
  @EventListener
  public void handleProductEvent(ProductEvent event) {
    try {
      switch (event.getType()) {
        case PRODUCT_CREATED -> corcaDataService.registerProduct(event.getProduct());
        case PRODUCT_UPDATED -> corcaDataService.updateProduct(event.getProduct());
        case PRODUCT_DELETED -> corcaDataService.deleteProduct(event.getProductId());
        case PRODUCT_RESTORED -> corcaDataService.restoreProduct(event.getProductId());
        case PRODUCTS_BULK_CREATED -> corcaDataService.registerProducts(event.getProducts());
        case PRODUCTS_BULK_UPDATED -> corcaDataService.updateProducts(event.getProducts());
      }
    } catch (Exception e) {
      log.error("Failed to process product event: {}", event.getType(), e);
    }
  }
}
