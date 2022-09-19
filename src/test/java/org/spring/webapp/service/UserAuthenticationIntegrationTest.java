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
package org.spring.webapp.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spring.webapp.config.WebSecurityConfigurationAware;

public class UserAuthenticationIntegrationTest extends WebSecurityConfigurationAware {

    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    final String username = "user";

    @Test
    @DisplayName("user not authenticated requires signin")
    public void requiresAuthentication() throws Exception {
      mockMvc.perform(get("/account/current"))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(redirectedUrl("http://localhost/signin"));
    }

    @Test
    @DisplayName("user authenticate success")
    public void userAuthenticates() throws Exception {

      mockMvc.perform(post("/authenticate").with(csrf()).param("username", username).param("password", "user"))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(redirectedUrl("/"))
        .andExpect(r -> Assertions.assertEquals(((SecurityContext) r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)).getAuthentication().getName(), username));

    }

    @Test
    @DisplayName("user authentication has failed correctly with wrong password")
    public void userAuthenticationFails() throws Exception {
      mockMvc.perform(post("/authenticate").with(csrf()).param("username", username).param("password", "invalid"))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(redirectedUrl("/signin?error=1"))
        .andExpect(r -> Assertions.assertNull(r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)));
    }
    
    @Test
    @DisplayName("user form login with security context")
    public void userFormLogin() throws Exception {
      mockMvc.perform(formLogin("/authenticate").user(username).password("user"))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(authenticated().withUsername(username))
        .andExpect(authenticated().withAuthentication(auth ->
          assertThat(auth).isInstanceOf(UsernamePasswordAuthenticationToken.class)));
    }
    
    @Test
    @DisplayName("user form login failed with security context")
    public void userFormLoginError() throws Exception {
      mockMvc.perform(formLogin("/authenticate").user(username))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(unauthenticated());
    }
    
    @Test
    @DisplayName("user form logoff")
    @WithMockUser(password="user")
    public void userFormLogout() throws Exception {
      mockMvc.perform(logout("/logout"))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(unauthenticated())
        .andExpect(redirectedUrl("/signin?logout"));
    }
    
    @Test
    @DisplayName("user form logoff not authenticated")
    public void userLogout() throws Exception {
      mockMvc.perform(logout("/logout"))
        .andDo(log()) // to activate change logger ".result" level to DEBUG in logback-test.xml
        .andExpect(unauthenticated())
        .andExpect(redirectedUrl("/signin?logout"));
    }
    
}
