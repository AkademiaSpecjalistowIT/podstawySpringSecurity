package pl.akademiaspecjalistowit.podstawyspringsecurity.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {


    @GetMapping
    public String getStudent() {
        return "jestem studentem";
    }

}
