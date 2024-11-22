package com.corca.ads.demo.corca.ads.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Corca Ads 상품 요청 DTO")
public class CorcaAdsProductRequestDTO extends BaseCorcaAdsRequestDTO {
}
