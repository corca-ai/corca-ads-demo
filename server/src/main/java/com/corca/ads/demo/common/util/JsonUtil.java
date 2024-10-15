package com.corca.ads.demo.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class JsonUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> List<T> readJsonFile(String fileName, Class<T> valueType) throws IOException {
    ClassPathResource resource = new ClassPathResource(fileName);
    return objectMapper.readValue(resource.getInputStream(),
        objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
  }
}
