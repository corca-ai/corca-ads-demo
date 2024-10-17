package com.corca.ads.demo.product.service;

import com.corca.ads.demo.common.util.JsonUtil;
import com.corca.ads.demo.product.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * ProductService 인터페이스의 구현체입니다. 이 서비스는 상품 데이터를 가져오기 위한 역할을 담당하며 Corca Ads API와의 연동과는 관련이 없습니다.
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  private final List<ProductDTO> products;

  public ProductServiceImpl() throws IOException {
    this.products = JsonUtil.readJsonFile("dummy-products.json", ProductDTO.class);
  }

  @Override
  public ProductDTO getProduct(String productId) {
    log.info("Fetching product details for product ID: {}", productId);

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
}
