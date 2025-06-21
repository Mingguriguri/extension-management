package com.madrascheck.extension_management.repository;

import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.entity.ExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    /**
     * 이름(대소문자 구분 없이)만으로 전체 중복 체크
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * 특정 타입 중복 체크
     */
    boolean existsByNameIgnoreCaseAndType(String name, ExtensionType type);

    /**
     * CUSTOM 타입 확장자의 총 개수를 반환
     */
    long countByType(ExtensionType type);
}

