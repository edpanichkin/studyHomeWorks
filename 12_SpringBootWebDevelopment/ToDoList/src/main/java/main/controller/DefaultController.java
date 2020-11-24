package main.controller;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class DefaultController
{
    @Autowired
    TaskRepository taskRepository;

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
        ArrayList<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksCount", tasks.size());
        return "index";
    }

}
