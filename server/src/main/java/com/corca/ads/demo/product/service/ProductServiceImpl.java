package com.corca.ads.demo.product.service;

import com.corca.ads.demo.product.dto.ProductDTO;
import com.corca.ads.demo.common.util.JsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final List<ProductDTO> products;

  public ProductServiceImpl() throws IOException {
    this.products = JsonUtil.readJsonFile("boribori-dummy-products.json", ProductDTO.class);
  }

  @Override
  public ProductDTO getProduct(String productId) {
    logger.info("Fetching product details for product ID: {}", productId);
    return products.stream().filter(product -> product.getId().equals(productId)).findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Product not found with id: " + productId));
  }

  @Override
  public Page<ProductDTO> getProducts(Pageable pageable) {
    logger.info("Fetching product list with page: {}", pageable.getPageNumber());

    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), products.size());

    List<ProductDTO> pageContent = products.subList(start, end);

    return new PageImpl<>(pageContent, pageable, products.size());
  }
}
