package org.info1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getInfo() {
        return "this is the info";
    }
}
