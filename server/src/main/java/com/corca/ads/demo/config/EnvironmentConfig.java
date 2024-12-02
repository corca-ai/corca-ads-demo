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
  public String corcaDataApiKeyId() {
    return env.getProperty("corca.data.api.key.id");
  }

  @Bean
  public String corcaDataApiKeySecret() {
    return env.getProperty("corca.data.api.key.secret");
  }
}
