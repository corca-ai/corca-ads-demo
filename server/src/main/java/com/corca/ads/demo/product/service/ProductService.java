package com.corca.ads.demo.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.corca.ads.demo.product.dto.ProductDTO;

public interface ProductService {
  ProductDTO getProduct(String productId);

  Page<ProductDTO> getProducts(Pageable pageable);
}
