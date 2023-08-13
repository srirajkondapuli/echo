package com.myown.echo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {
      @GetMapping("/home")
    public String homeEndpoint() {
        return "Echo Service !";
    }
}
