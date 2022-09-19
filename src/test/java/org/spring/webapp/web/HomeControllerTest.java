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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;


@Tag("home controller")
public class HomeControllerTest extends WebSecurityConfigurationAware {
    
  @Autowired
  protected MockHttpSession session;
  
  /**
   * Test method for {@link org.spring.webapp.web.HomeController#index(java.security.Principal, org.springframework.ui.Model)}.
   */
  @Test
  @DisplayName("show home page no credentials")
  public void displayHomePage() throws Exception {
    mockMvc.perform(get("/").with(csrf().asHeader()))
      .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("module"))
      .andExpect(view().name("home/homeNotSignedIn"))
      .andExpect(content().string(
          allOf(
              containsString("Welcome to the Spring"),
              containsString("<p>\n" + 
                  "            Get started quickly by signing up.\n" + 
                  "        </p>")
          ))
      );
  }
  
  /**
   * Test method for {@link org.spring.webapp.web.HomeController#index()}.
   */
  @Test
  @DisplayName("show home page with credentials")
  @WithMockUser(password="user")
  public void displayHomePageWithLogin() throws Exception {
    mockMvc.perform(get("/").with(csrf().asHeader()))
      .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
      .andExpect(status().isOk())
      .andExpect(model().attributeExists("module"))
      .andExpect(view().name("home/homeSignedIn"))
      .andExpect(content().string(
          allOf(
              containsString("<title>Home page</title>"),
              containsString("Welcome to the Spring")
          ))
      );
  }

}
