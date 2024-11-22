package com.corca.ads.demo.product.event;

import com.corca.ads.demo.corca.data.CorcaDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
