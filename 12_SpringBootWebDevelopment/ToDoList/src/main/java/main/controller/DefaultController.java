package main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController
{
    @RequestMapping("/date")
    public String date(){
        return (new Date()).toString();
    }
    @RequestMapping("/random")
    public String random(){
        return String.valueOf(Math.random());
    }
}
