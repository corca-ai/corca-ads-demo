package com.corca.ads.demo.product.event;

import com.corca.ads.demo.product.dto.ProductDTO;
import lombok.Getter;

import java.util.List;

/*
 * 스토어에서 관리 하는 상품 데이터에 변경이 있을 때 발생하는 이벤트입니다.
 * 
 * 데모 코드에서는 Corca Data API의 명세에 따라 단일 상품, 여러 상품, 상품 ID만을 사용하는 작업으로 구분하고 각 이벤트는 비동기로 처리되어 전송됩니다.
 */
@Getter
public class ProductEvent {
  public enum EventType {
    // @formatter:off
    PRODUCT_CREATED(EventCategory.SINGLE),       // 단일 상품 생성
    PRODUCT_UPDATED(EventCategory.SINGLE),       // 단일 상품 수정
    PRODUCT_DELETED(EventCategory.ID_ONLY),      // 상품 삭제
    PRODUCT_RESTORED(EventCategory.ID_ONLY),     // 상품 복구
    PRODUCTS_BULK_CREATED(EventCategory.BULK),   // 상품 일괄 등록
    PRODUCTS_BULK_UPDATED(EventCategory.BULK);   // 상품 일괄 수정
    // @formatter:on

    private final EventCategory category;

    EventType(EventCategory category) {
      this.category = category;
    }
  }

  private enum EventCategory {
    // @formatter:off
    SINGLE,
    BULK,
    ID_ONLY
    // @formatter:on
  }

  private final EventType type;
  private final ProductDTO product;
  private final List<ProductDTO> products;
  private final String productId;

  private ProductEvent(EventType type, ProductDTO product, List<ProductDTO> products,
      String productId) {
    this.type = type;
    this.product = product;
    this.products = products;
    this.productId = productId;
  }

  private static void validateEventType(EventType type, EventCategory expectedCategory) {
    if (type.category != expectedCategory) {
      throw new IllegalArgumentException(
          String.format("Invalid event type %s for %s operation", type, expectedCategory));
    }
  }

  public static ProductEvent createSingleEvent(EventType type, ProductDTO product) {
    validateEventType(type, EventCategory.SINGLE);

    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null for single events");
    }

    return new ProductEvent(type, product, null, product.getId());
  }

  public static ProductEvent createBulkEvent(EventType type, List<ProductDTO> products) {
    validateEventType(type, EventCategory.BULK);

    if (products == null || products.isEmpty()) {
      throw new IllegalArgumentException("Products list cannot be null or empty for bulk events");
    }

    return new ProductEvent(type, null, products, null);
  }

  public static ProductEvent createIdOnlyEvent(EventType type, String productId) {
    validateEventType(type, EventCategory.ID_ONLY);

    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }

    return new ProductEvent(type, null, null, productId);
  }
}
