package com.madrascheck.extension_management.repository;

import com.madrascheck.extension_management.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {
}

