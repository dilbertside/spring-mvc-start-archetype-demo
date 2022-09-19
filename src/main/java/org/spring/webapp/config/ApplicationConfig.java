package org.spring.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.spring.webapp.Application;

@Configuration
@PropertySource("classpath:" + ApplicationConfig.APPLICATION_PROPERTIES_FILENAME)
@ComponentScan(basePackageClasses = Application.class)
public class ApplicationConfig {
  
  final public static String APPLICATION_PROPERTIES_FILENAME = "application.properties";

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
