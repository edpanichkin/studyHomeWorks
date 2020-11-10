package main.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DefaultControllerTest extends AbstractIntegrationTest {
    @Test
    public void shouldReturnSomething() throws Exception {
        String httpDate = "http://localhost:8080/date";
        String httpRnd = "http://localhost:8080/random";
        mockMvc.perform(MockMvcRequestBuilders.get(httpDate)) // request_mapping_path это наш URL по которому доступен REST API
            .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get(httpRnd)) // request_mapping_path это наш URL по которому доступен REST API
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

