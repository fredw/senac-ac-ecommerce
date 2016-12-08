package com.ddf.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PanelHomeController {

    @RequestMapping("/painel")
    public String index() {
        return "panel/home";
    }
}
