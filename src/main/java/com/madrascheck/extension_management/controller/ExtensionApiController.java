package com.madrascheck.extension_management.controller;

import com.madrascheck.extension_management.dto.ExtensionResponse;
import com.madrascheck.extension_management.dto.ExtensionUpdateRequest;
import com.madrascheck.extension_management.dto.ExtensionCreateRequest;
import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.service.ExtensionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extensions")
@Tag(name = "Extension API Controller", description = "Response Estimate API")
public class ExtensionApiController {
    private final ExtensionServiceImpl extensionService;
    public ExtensionApiController(ExtensionServiceImpl extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation( summary = "전체 확장자 목록 조회",
                description = "FIXED + CUSTOM 확장자와 각 확장자의 차단(enabled) 상태를 반환")
    public List<ExtensionResponse> listAll() {
        return extensionService.findAll().stream()
                .map(ExtensionResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation( summary = "커스텀 확장자 추가",
                description = "name에 입력된 값을 CUSTOM 확장자로 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "정상적으로 생성하였습니다",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExtensionResponse.class))
        ),
            @ApiResponse(responseCode = "400", description = "최대 개수(200개) 초과하였습니다",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 확장자입니다.",
                    content = @Content(mediaType = "application/json")
            )
    })
    @Parameters({
            @Parameter(name = "name", description = "확장자 이름 (20자 이내)", example = "ext"),
    })
    public ExtensionResponse create(
            @RequestBody @Validated ExtensionCreateRequest req) {
        Extension created = extensionService.addCustom(req.getName());
        return ExtensionResponse.from(created);
    }

    @PatchMapping("/{id}")
    @Operation( summary = "FIXED 확장자 차단 여부 토글",
                description = "FIXED 타입에 한해 enabled 값을 변경")
    @Parameters({
            @Parameter(name = "enabled", description = "FIXED 확장자 토글 여부 (boolean)", example = "true"),
    })
    public ExtensionResponse toggle(
            @PathVariable Long id,
            @RequestBody @Validated ExtensionUpdateRequest req) {
        Extension updated = extensionService.toggleFixed(id, req.isEnabled());
        return ExtensionResponse.from(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "커스텀 확장자 삭제",
            description = "CUSTOM 타입인 경우에만 삭제 가능")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        extensionService.deleteCustom(id);
    }
}
