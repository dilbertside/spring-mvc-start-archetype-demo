package org.spring.webapp.config;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * 
 */
@Configuration
public class JacksonConfiguration {

  /**
   * Jackson Afterburner module to speed up serialization/deserialization.
   */
  @Bean
  public AfterburnerModule afterburnerModule() {
    return new AfterburnerModule();
  }

  /**
   * Module for serialization/deserialization of RFC7807 Problem.
   */
  @Bean
  org.zalando.problem.jackson.ProblemModule problemModule(Environment environment) {
    ProblemModule problemModule = new ProblemModule();
    problemModule = problemModule.withStackTraces(environment.acceptsProfiles(Profiles.of("prod")) ? false : true);
    return problemModule;
  }

  /**
   * Module for serialization/deserialization of ConstraintViolationProblem.
   */
  @Bean
  ConstraintViolationProblemModule constraintViolationProblemModule() {
    return new ConstraintViolationProblemModule();
  }

}
