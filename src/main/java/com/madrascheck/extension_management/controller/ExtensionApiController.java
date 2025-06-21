package com.madrascheck.extension_management.controller;

import com.madrascheck.extension_management.dto.ExtensionResponse;
import com.madrascheck.extension_management.dto.ExtensionUpdateRequest;
import com.madrascheck.extension_management.dto.ExtensionCreateRequest;
import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.service.ExtensionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionApiController {
    private final ExtensionServiceImpl extensionService;
    public ExtensionApiController(ExtensionServiceImpl extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping
    public List<ExtensionResponse> listAll() {
        return extensionService.findAll().stream()
                .map(ExtensionResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExtensionResponse create(
            @RequestBody @Validated ExtensionCreateRequest req) {
        Extension created = extensionService.addCustom(req.getName());
        return ExtensionResponse.from(created);
    }

    @PatchMapping("/{id}")
    public ExtensionResponse toggle(
            @PathVariable Long id,
            @RequestBody @Validated ExtensionUpdateRequest req) {
        Extension updated = extensionService.toggleFixed(id, req.isEnabled());
        return ExtensionResponse.from(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        extensionService.deleteCustom(id);
    }
}
