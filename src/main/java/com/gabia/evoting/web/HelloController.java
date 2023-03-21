package com.gabia.evoting.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * TEST Controller
 * 인증 테스트 및 rest api 작동 확인을 위한 컨트롤러
 */
@RestController
@ApiIgnore
public class HelloController {
    @GetMapping("/api/hello1")
    public String hello1() {
        return "hello";
    }

    @GetMapping("/api/hello2")
    public String hello2(@RequestParam String param) {
        return param;
    }
}
