package com.library.backend_epilogo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API")

public class UsuarioController {
    @GetMapping("/PRUEBA")
    public String prueba() {
        return "Prueba";
    }
}
