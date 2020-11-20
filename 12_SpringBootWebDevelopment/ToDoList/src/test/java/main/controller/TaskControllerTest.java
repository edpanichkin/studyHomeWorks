package main.controller;

import main.model.TaskRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TaskControllerTest extends AbstractIntegrationTest{
    public final String rootPath = "http://localhost:8080";
    @Autowired
    TaskRepository taskRepository;
    @Test
    public void add() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post(rootPath + "/tasks")
//                .param("taskName", "task_name")
//                .param("done","true"))
//                .andExpect(MockMvcResultMatchers.);
    }
    @Test
    public void getNOIdList () throws Exception {
        //String requestMappingPath = "http://localhost:8080/0";
        mockMvc.perform(MockMvcRequestBuilders.get(rootPath + "/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void getIdList () throws Exception {
        String requestMappingPath = "http://localhost:8080/1";
        mockMvc.perform(MockMvcRequestBuilders.get(requestMappingPath))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
