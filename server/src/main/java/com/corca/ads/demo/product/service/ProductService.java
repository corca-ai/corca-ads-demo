package com.corca.ads.demo.product.service;

import com.corca.ads.demo.product.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
  ProductDTO getProduct(String productId);

  Page<ProductDTO> getProducts(Pageable pageable);

  ProductDTO createProduct(ProductDTO product);

  List<ProductDTO> createProducts(List<ProductDTO> newProducts);

  ProductDTO updateProduct(String productId, ProductDTO product);

  List<ProductDTO> updateProducts(List<ProductDTO> updatedProducts);

  void deleteProduct(String productId);

  void restoreProduct(String productId);
}
