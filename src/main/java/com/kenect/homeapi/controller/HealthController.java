package com.kenect.homeapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/health")
public class HealthController {

    @GetMapping
    public String isHealth(){

        return "Home API Running with health";
    }
}
