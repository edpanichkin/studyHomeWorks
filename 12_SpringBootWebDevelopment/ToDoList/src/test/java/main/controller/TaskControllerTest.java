package main.controller;

import main.dao.TaskService;
import main.model.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest extends AbstractIntegrationTest{
    @Autowired
    TaskService taskService;

    private TaskModel createTestTask(String name, String about) {
        return taskService.addTask(new TaskModel(name, about));
    }

    @Test
    public void givenId_whenGetExistingTask_thenStatus200andTaskReturned() throws Exception {
        int id = createTestTask("createTestTask", "some task").getId();
        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("createTestTask"))
                .andExpect(jsonPath("$.about").value("some task"));
    }
    @Test
    public void givenTask_whenAdd_thenStatus201andTaskReturned() throws Exception {
        mockMvc.perform(post("/tasks/").contentType(MediaType.APPLICATION_JSON)
                .param("name", "PostNewTestTask")
                .param("about", "testAbout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("PostNewTestTask"))
                .andExpect(jsonPath("$.about").value("testAbout"));
    }
    @Test
    public void givenTask_whenDeleteTask_thenStatus200() throws Exception {
        TaskModel task = createTestTask("testTaskDelete", "for test delete");
        mockMvc.perform(delete("/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(task)));
    }
    @Test
    public void giveTask_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        long id = createTestTask("beforePutTask","aboutBefore").getId();
        mockMvc.perform( //test only change NAME
                put("/tasks/{id}", id)
                        .param("name", "afterPutTask")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("afterPutTask"))
                .andExpect(jsonPath("$.about").value("aboutBefore"))
                .andExpect(jsonPath("$.done").value("false"));

        mockMvc.perform( //test only change ABOUT
                put("/tasks/{id}", id)
                        .param("about", "aboutAfter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("afterPutTask"))
                .andExpect(jsonPath("$.about").value("aboutAfter"))
                .andExpect(jsonPath("$.done").value("false"));
        mockMvc.perform( //test only change DONE
                put("/tasks/{id}", id)
                        .param("done", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("afterPutTask"))
                .andExpect(jsonPath("$.about").value("aboutAfter"))
                .andExpect(jsonPath("$.done").value("true"));
    }
    @Test
    public void giveNonExistentId_GET_shouldReturn404 () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/0"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void giveNonExistentId_DELETE_shouldReturn404 () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/0"))
                .andExpect(status().isNotFound());
    }
}
