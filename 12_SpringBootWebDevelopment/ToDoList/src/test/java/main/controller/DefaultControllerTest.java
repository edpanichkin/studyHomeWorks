package main.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DefaultControllerTest extends AbstractIntegrationTest {
    @Test
    public void shouldReturnSuccessStatusCodeForDateEndpoint() throws Exception {
        String requestMappingPath = "http://localhost:8080/date";
        mockMvc.perform(MockMvcRequestBuilders.get(requestMappingPath)) // request_mapping_path это наш URL по которому доступен REST API
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnSuccessStatusCodeForRandomEndpoint() throws Exception {
        String requestMappingPath = "http://localhost:8080/random";
        mockMvc.perform(MockMvcRequestBuilders.get(requestMappingPath)) // request_mapping_path это наш URL по которому доступен REST API
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

