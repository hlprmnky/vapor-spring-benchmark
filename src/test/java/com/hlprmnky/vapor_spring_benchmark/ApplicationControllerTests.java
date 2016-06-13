/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hlprmnky.vapor_spring_benchmark;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Turnquist
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class ApplicationControllerTests {

	@Autowired
	private WebApplicationContext ctx;

    @Autowired
    private UserRepository userRepository;

	private MockMvc mockMvc;

    private final ObjectMapper MAPPER = new ObjectMapper();
    private final List<User> testUsers = Arrays.asList(new User("Foo", "foo@gmail.com"),
            new User("Bar", "bar@gmail.com"),
            new User("Baz", "baz@gmail.com"));

	@Before
	public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        userRepository.deleteAll();
        userRepository.save(testUsers);
	}

	@Test
	public void testJsonEndpoint() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/json"))
				.andDo(print())
				.andExpect(status().isOk())
                .andReturn();

        assertThat(new Json().equals(MAPPER.readValue(result.getResponse().getContentAsString(), Json.class)));
	}

	@Test
	public void testPlaintextEndpoint() throws Exception {

		this.mockMvc.perform(get("/plaintext"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Hello, World!"));
	}

    @Test
    public void testSqliteFetchEndpoint() throws  Exception {
        for(int i = 0; i < 10; i++) {
            MvcResult result = this.mockMvc.perform(get("/sqlite-fetch"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            assertThat(testUsers.contains(MAPPER.readValue(result.getResponse().getContentAsString(), User.class)));
        }
    }
}
