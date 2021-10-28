package com.ziyear.volcano.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-27 11:14
 */
@RestController
public class TestResource {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
