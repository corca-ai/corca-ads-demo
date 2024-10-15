package com.corca.ads.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Configuration
public class EnvironmentConfig {

  private final Environment env;

  public EnvironmentConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public String corcaAdsApiKey() {
    return env.getProperty("corca.ads.api.key");
  }

  @Bean
  public String corcaAdsClientId() {
    return env.getProperty("corca.ads.api.client.id");
  }
}
