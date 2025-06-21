package com.madrascheck.extension_management.repository;

import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.entity.ExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndType(String name, ExtensionType type);
}

