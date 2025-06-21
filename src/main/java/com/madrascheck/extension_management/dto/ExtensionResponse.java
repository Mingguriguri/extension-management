package com.madrascheck.extension_management.dto;

import com.madrascheck.extension_management.entity.Extension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtensionResponse {
    private Long id;
    private String name;
    private String type;
    private boolean enabled;
    private LocalDateTime createdAt;

    public static ExtensionResponse from(Extension e) {
        ExtensionResponse r = new ExtensionResponse();
        r.id = e.getId();
        r.name = e.getName();
        r.type = e.getType().name();
        r.enabled = e.isEnabled();
        r.createdAt = e.getCreatedAt();
        return r;
    }
}
