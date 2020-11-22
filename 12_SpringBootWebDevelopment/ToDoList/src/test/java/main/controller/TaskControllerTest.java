package main.controller;

import main.model.Task;
import main.model.TaskRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends AbstractIntegrationTest{
    public final String rootPath = "http://localhost:8080";
    @Autowired
    TaskRepository taskRepository;

    private Task createTestTask(String name, String about) {
        Task task = new Task(name, about);
        return taskRepository.save(task);
    }
    @Test
    public void givenId_whenGetExistingTask_thenStatus200andTaskReturned() throws Exception {
        int id = createTestTask("TestTask", "some task").getId();
        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("TestTask"))
                .andExpect(jsonPath("$.about").value("some task"));
    }
    @Test
    public void givenPerson_whenAdd_thenStatus201andPersonReturned() throws Exception {
        Task task = new Task("testTask", "for test");
        System.out.println(mapper.writeValueAsString(task));
        mockMvc.perform(post(rootPath + "/tasks/")
                        .content(mapper.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(jsonPath("$.name").value("testTask"))
                .andExpect(jsonPath("$.about").value("for test"))
                .andExpect(status().isCreated());
    }
//    @Test
//    public void add() throws Exception {
////        mockMvc.perform(MockMvcRequestBuilders.post(rootPath + "/tasks")
////                .param("taskName", "task_name")
////                .param("done","true"))
////                .andExpect(MockMvcResultMatchers.);
//    }
    @Test
    public void getNOIdList () throws Exception {
        //String requestMappingPath = "http://localhost:8080/0";
        mockMvc.perform(MockMvcRequestBuilders.get(rootPath + "/tasks/0"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getIdList () throws Exception {
        String requestMappingPath = "/tasks/2";
        mockMvc.perform(MockMvcRequestBuilders.get(requestMappingPath))
                .andExpect(status().isOk());
    }
}
