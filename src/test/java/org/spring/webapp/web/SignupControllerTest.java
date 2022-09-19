/**
 *  The MIT License (MIT)
 *
 * Copyright © 2019-2022 dilbertside
 *
 * Copyright 2019-2022 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.spring.webapp.web;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.spring.webapp.config.WebSecurityConfigurationAware;

@Tag("signupcontroller")
public class SignupControllerTest extends WebSecurityConfigurationAware {
    
  /**
   * Test method for {@link org.spring.webapp.web.SignupController#signup(org.spring.webapp.dto.SignupForm, org.springframework.validation.Errors, org.springframework.web.servlet.mvc.support.RedirectAttributes)}.
   */
  @Test
  @DisplayName("show signup form")
  public void displaySignupForm() throws Exception {
    mockMvc.perform(get("/signup").with(csrf().asHeader()))
      .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("signupForm"))
      .andExpect(view().name("signup/signup"))
      .andExpect(content().string(
          allOf(
              containsString("<title>Signup</title>"),
              containsString("<legend>Please Sign Up</legend>")
          ))
      );
  }
  
  @Test
  @DisplayName("proceed signup form")
  public void signupForm() throws Exception {
    mockMvc.perform(post("/signup")
        .with(csrf().asHeader())
        .param("email", "john@doe.com")
        .param("password", "doe")
      )
    .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
    .andExpect(status().isFound())
    .andExpect(redirectedUrl("/"))
    ;
  }
}
