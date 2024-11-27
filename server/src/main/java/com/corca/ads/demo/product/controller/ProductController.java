package com.corca.ads.demo.product.controller;

import com.corca.ads.demo.product.dto.ProductDTO;
import com.corca.ads.demo.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * 상품 관련 API 요청을 처리하는 컨트롤러입니다. 이 컨트롤러는 기존 상품을 가져오기 위한 엔드포인트를 제공합니다.
 */
@RestController
@Tag(name = "Product", description = "광고 상품이 아닌 기존의 상품을 조회하는 데 사용되는 API")
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /*
   * 📍 참고: 아래 두개의 엔드포인트는 Corca Ads API와 직접적인 연관이 없으며, 데모 웹 사이트를 위해 간단히 구현되어 참고하지 않으셔도 무관합니다.
   * 
   * 📍 참고: 데모에서는 실제 DB 대신 더미 JSON 파일에서 상품 데이터를 조회합니다.
   */

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
  @Operation(summary = "상품 목록", description = "상품 목록을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "Success",
      content = @Content(schema = @Schema(implementation = Page.class)))
  public Page<ProductDTO> getProducts(@Parameter(description = "페이지 정보",
      schema = @Schema(implementation = Pageable.class)) @PageableDefault(
          size = 10) Pageable pageable) {
    return productService.getProducts(pageable);
  }

  /*
   * 📍 참고: 아래 여섯개의 엔드포인트는 Corca Data API의 연동 예시를 보여드리기 위해 작성된 코드입니다.
   * 
   * 📍 참고: 데모에서는 DB 사용과 비즈니스 로직을 포함하지 않습니다.
   */

  @PostMapping
  @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다. Corca Data API로도 전송됩니다.")
  @ApiResponse(responseCode = "201", description = "Success")
  public ProductDTO createProduct(@RequestBody ProductDTO product) {
    return productService.createProduct(product);
  }

  @PostMapping("/bulk")
  @Operation(summary = "상품 일괄 등록", description = "여러 상품을 한 번에 등록합니다. Corca Data API로도 전송됩니다.")
  @ApiResponse(responseCode = "201", description = "Success")
  public List<ProductDTO> createProducts(@RequestBody List<ProductDTO> products) {
    return productService.createProducts(products);
  }

  @PutMapping("/{productId}")
  @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다. Corca Data API로도 전송됩니다.")
  @ApiResponse(responseCode = "200", description = "Success")
  public ProductDTO updateProduct(@PathVariable String productId, @RequestBody ProductDTO product) {
    return productService.updateProduct(productId, product);
  }

  @PutMapping("/bulk")
  @Operation(summary = "상품 일괄 수정", description = "여러 상품 정보를 한 번에 수정합니다. Corca Data API로도 전송됩니다.")
  public List<ProductDTO> updateProducts(@RequestBody List<ProductDTO> products) {
    return productService.updateProducts(products);
  }

  @DeleteMapping("/{productId}")
  @Operation(summary = "상품 삭제", description = "상품을 삭제 처리합니다. Corca Data API로도 전송됩니다.")
  public void deleteProduct(@PathVariable String productId) {
    productService.deleteProduct(productId);
  }

  @PostMapping("/{productId}/restore")
  @Operation(summary = "상품 복구", description = "삭제된 상품을 복구합니다. Corca Data API로도 전송됩니다.")
  public void restoreProduct(@PathVariable String productId) {
    productService.restoreProduct(productId);
  }
}
