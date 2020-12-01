package main.controller;

import main.dao.TaskService;
import main.model.TaskModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class DefaultController
{
    final TaskService taskService;

    public DefaultController(TaskService taskService) {
        this.taskService = taskService;
    }
    @Value("${someParameter}")
    private int someParameter;
    @RequestMapping("/date")
    public String date(Model model){
        model.addAttribute("date", (new Date()).toString());
        return "date";
    }
    @RequestMapping("/random")
    public String random(Model model) {
        model.addAttribute("random", Math.random());
        return "random";
    }
    @RequestMapping("/")
    public String index(Model model){
        List<TaskModel> tasks = taskService.taskList();
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksCount", tasks.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }

}
