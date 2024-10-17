package com.corca.ads.demo.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "보리보리 상품 DTO")
public class ProductDTO {
  private String id;
  private String name;
  private double price;
  // todo: define extra fields
}
