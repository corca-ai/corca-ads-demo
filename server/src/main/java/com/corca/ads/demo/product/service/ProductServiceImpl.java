package com.corca.ads.demo.product.service;

import com.corca.ads.demo.common.util.JsonUtil;
import com.corca.ads.demo.product.dto.ProductDTO;
import com.corca.ads.demo.product.event.ProductEvent;
import com.corca.ads.demo.product.event.ProductEvent.EventType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/*
 * ProductService 인터페이스의 구현체입니다.
 * 
 * 이 서비스는 스토어의 기존 상품 데이터를 가져오거나 상품을 관리하는 역할을 담당하며, 상품 관리시에는 Corca Data API와 연동하여 상품 데이터를 동기화합니다.
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  private final List<ProductDTO> products;
  private final ApplicationEventPublisher eventPublisher;

  public ProductServiceImpl(ApplicationEventPublisher eventPublisher) throws IOException {
    this.products = JsonUtil.readJsonFile("dummy-products.json", ProductDTO.class);
    this.eventPublisher = eventPublisher;
  }

  @Override
  public ProductDTO getProduct(String productId) {
    log.info("Fetching product details for product ID: {}", productId);
    log.info("Product list: {}", products.size());

    return products.stream().filter(product -> product.getId().equals(productId)).findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Product not found with id: " + productId));
  }

  @Override
  public Page<ProductDTO> getProducts(Pageable pageable) {
    log.info("Fetching product list with page: {}", pageable.getPageNumber());

    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), products.size());

    List<ProductDTO> pageContent = products.subList(start, end);

    return new PageImpl<>(pageContent, pageable, products.size());
  }

  /*
   * Corca Data API와 연동하여 상품 데이터를 동기화하는 방식으로는 여러 가지 방법이 있을 수 있습니다.
   * 
   * 데모 코드에서는 외부 API의 응답이 기존에 동작하던 스토어의 상품 관리 로직의 성공 여부에 지장을 주지 않도록 이벤트를 발행하는 방식으로 작성되었습니다.
   * 
   * 연동 실패 시에는 메세지 큐 등을 활용하여 적절히 재시도를 하는 것을 권장드립니다.
   */

  @Override
  public ProductDTO createProduct(ProductDTO product) {
    // 주석으로 스토어의 DB에 단일 상품을 추가하는 코드를 대체합니다.

    eventPublisher.publishEvent(ProductEvent.createSingleEvent(EventType.PRODUCT_CREATED, product));

    return product;
  }

  @Override
  public List<ProductDTO> createProducts(List<ProductDTO> newProducts) {
    // 주석으로 스토어의 DB에 여러 상품을 추가하는 코드를 대체합니다.

    eventPublisher
        .publishEvent(ProductEvent.createBulkEvent(EventType.PRODUCTS_BULK_CREATED, newProducts));

    return newProducts;
  }

  @Override
  public ProductDTO updateProduct(String productId, ProductDTO product) {
    // 주석으로 스토어의 DB에 단일 상품을 업데이트하는 코드를 대체합니다.

    eventPublisher.publishEvent(ProductEvent.createSingleEvent(EventType.PRODUCT_UPDATED, product));

    return product;
  }

  @Override
  public List<ProductDTO> updateProducts(List<ProductDTO> updateProducts) {
    // 주석으로 스토어의 DB에 여러 상품을 업데이트하는 코드를 대체합니다.

    eventPublisher.publishEvent(
        ProductEvent.createBulkEvent(EventType.PRODUCTS_BULK_UPDATED, updateProducts));

    return updateProducts;
  }

  @Override
  public void deleteProduct(String productId) {
    // 주석으로 스토어의 DB에서 상품을 삭제하는 코드를 대체합니다.

    eventPublisher
        .publishEvent(ProductEvent.createIdOnlyEvent(EventType.PRODUCT_DELETED, productId));
  }

  @Override
  public void restoreProduct(String productId) {
    // 주석으로 스토어의 DB에서 상품을 복원하는 코드를 대체합니다.

    eventPublisher
        .publishEvent(ProductEvent.createIdOnlyEvent(EventType.PRODUCT_RESTORED, productId));
  }
}
