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
  private double discountPrice;
  private String image;
  private String summary;
  // todo: define extra fields for boribori product schema
}
