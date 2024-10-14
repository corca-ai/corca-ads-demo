package com.corca.ads.demo.product.service;

import com.corca.ads.demo.product.dto.ProductDTO;

public interface ProductService {
  ProductDTO getProduct(String productId);
}
