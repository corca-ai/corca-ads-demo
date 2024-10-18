package com.corca.ads.demo.product.service;

import com.corca.ads.demo.product.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  ProductDTO getProduct(String productId);

  Page<ProductDTO> getProducts(Pageable pageable);
}
