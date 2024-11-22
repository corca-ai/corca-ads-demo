package com.corca.ads.demo.product.event;

import com.corca.ads.demo.product.dto.ProductDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductEvent {
  public enum EventType {
    // @formatter:off
    PRODUCT_CREATED(EventCategory.SINGLE),
    PRODUCT_UPDATED(EventCategory.SINGLE),
    PRODUCT_DELETED(EventCategory.ID_ONLY),
    PRODUCT_RESTORED(EventCategory.ID_ONLY),
    PRODUCTS_BULK_CREATED(EventCategory.BULK),
    PRODUCTS_BULK_UPDATED(EventCategory.BULK);
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
