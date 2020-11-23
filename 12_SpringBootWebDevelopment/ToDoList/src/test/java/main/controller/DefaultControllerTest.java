package main.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DefaultControllerTest extends AbstractIntegrationTest {
    @Test
    public void dateEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/date")) // request_mapping_path это наш URL по которому доступен REST API
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void randomEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/random")) // request_mapping_path это наш URL по которому доступен REST API
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

