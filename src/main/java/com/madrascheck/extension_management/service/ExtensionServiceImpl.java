package com.madrascheck.extension_management.service;

import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.entity.ExtensionType;
import com.madrascheck.extension_management.exception.DuplicateExtensionException;
import com.madrascheck.extension_management.repository.ExtensionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionServiceImpl implements ExtensionService {
    private final ExtensionRepository extensionRepository;

    public ExtensionServiceImpl(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    @Override
    public List<Extension> findAll() {
        return extensionRepository.findAll();
    }

    @Override
    public Extension addCustom(String name) {
        String normalizedName = name.trim().toLowerCase();

        // FIXED 목록에 이미 있는지 검사
        if (extensionRepository.existsByNameIgnoreCaseAndType(normalizedName, ExtensionType.FIXED)) {
            throw new DuplicateExtensionException("이미 시스템 고정 확장자에 등록된 이름입니다: " + name);
        }

        // CUSTOM 목록에 이미 있는지 검사
        if (extensionRepository.existsByNameIgnoreCaseAndType(normalizedName, ExtensionType.CUSTOM)) {
            throw new DuplicateExtensionException("이미 추가된 커스텀 확장자입니다: " + name);
        }

        Extension ext= new Extension(normalizedName, ExtensionType.CUSTOM);

        return extensionRepository.save(ext);
    }

    @Override
    public Extension toggleFixed(Long id, boolean enabled) {
        Extension ext = extensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Extension ID " + id + " not found"));
        if (ext.getType() != ExtensionType.FIXED) {
            throw new IllegalArgumentException("ID " + id + " is not a FIXED extension");
        }
        // TODO: 현재 상태와 같은지 다른지 확인.
        ext.changeEnabled(enabled);

        return extensionRepository.save(ext);
    }

    @Override
    public void deleteCustom(Long id) {
        Extension ext = extensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + " not found"));
        if (ext.getType() != ExtensionType.CUSTOM) {
            throw new IllegalArgumentException("ID " + id + " is not a CUSTOM extension");
        }
        extensionRepository.delete(ext);
    }

}
