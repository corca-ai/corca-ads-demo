package com.corca.ads.demo.product.controller;

import com.corca.ads.demo.product.service.ProductService;
import com.corca.ads.demo.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 상품 관련 API 요청을 처리하는 컨트롤러입니다.
 * 
 * 📍 참고: 이 컨트롤러는 Corca Ads API와 직접적인 연관이 없으며, 데모 웹 사이트를 위해 간단히 구현되어 참고하지 않으셔도 무관합니다.
 * 
 * 📍 참고: 데모에서는 실제 DB 대신 더미 JSON 파일에서 상품 데이터를 조회합니다.
 */
@RestController
@Tag(name = "Product", description = "광고 상품이 아닌 기존의 상품을 조회하는 데 사용되는 API")
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * 상품 ID를 기반으로 상품 상세 정보를 조회합니다.
   * 
   * @param productId 조회할 상품 ID
   * @return 조회된 상품 상세 정보를 담은 ProductDetailDTO
   */
  @GetMapping("/{productId}")
  @Operation(summary = "상품 상세 정보", description = "상품 ID를 기반으로 상품 상세 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "Success",
      content = @Content(schema = @Schema(implementation = ProductDTO.class)))
  public ProductDTO getProduct(
      @Parameter(description = "조회할 상품 ID") @PathVariable String productId) {
    return productService.getProduct(productId);
  }

  /**
   * 상품 목록을 조회합니다.
   * 
   * @param pageable 페이지 정보 (페이지 번호, 페이지 크기 등)
   * @return 조회된 상품 목록
   */
  @GetMapping
  public Page<ProductDTO> getProducts(@Parameter(description = "페이지 정보",
      schema = @Schema(implementation = Pageable.class)) @PageableDefault(
          size = 10) Pageable pageable) {
    return productService.getProducts(pageable);
  }
}
