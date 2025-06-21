package com.madrascheck.extension_management.controller;

import org.springframework.ui.Model;
import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.entity.ExtensionType;
import com.madrascheck.extension_management.service.ExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExtensionController {
    private final ExtensionService service;
    public ExtensionController(ExtensionService service) {
        this.service = service;
    }

    @GetMapping("/extensions")
    public String viewPage(Model model) {
        List<Extension> all = service.findAll();
        model.addAttribute("fixedExtensions",
                all.stream().filter(e->e.getType()== ExtensionType.FIXED).toList());
        model.addAttribute("customExtensions",
                all.stream().filter(e->e.getType()==ExtensionType.CUSTOM).toList());
        return "extensions";
    }
}
