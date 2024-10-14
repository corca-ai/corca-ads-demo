package com.corca.ads.demo.product.controller;

import com.corca.ads.demo.product.service.ProductService;
import com.corca.ads.demo.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 상품 관련 API 요청을 처리하는 컨트롤러입니다.
 * 
 * 📍 참고: 이 컨트롤러는 Corca Ads API와 직접적인 연관이 없으며, 데모 웹 사이트를 위해 간단히 구현되어 참고하지 않으셔도 무관합니다.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * 상품 ID를 기반으로 상품 상세 정보를 조회합니다.
   * 
   * 📍 참고: 데모에서는 실제 DB 대신 더미 JSON 파일에서 상품 데이터를 조회합니다.
   * 
   * @param productId 조회할 상품 ID
   * @return 조회된 상품 상세 정보를 담은 ProductDetailDTO
   */
  @GetMapping("/{productId}")
  public ProductDTO getProduct(@PathVariable String productId) {
    return productService.getProduct(productId);
  }
}
