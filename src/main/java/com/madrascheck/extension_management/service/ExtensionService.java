package com.madrascheck.extension_management.service;

import com.madrascheck.extension_management.entity.Extension;

import java.util.List;

public interface ExtensionService{
    /**
     * 전체 확장자 조회 (FIXED + CUSTOM)
     */
    List<Extension> findAll();

    /**
     * CUSTOM 타입 확장자 생성
     */
    Extension addCustom(String name);

    /**
     * FIXED 타입 확장자 차단(enabled) 토글
     */
    Extension toggleFixed(Long id, boolean enabled);

    /**
     * CUSTOM 타입 확장자 삭제
     */
    void deleteCustom(Long id);
}
