package com.madrascheck.extension_management.service;

import com.madrascheck.extension_management.entity.Extension;
import com.madrascheck.extension_management.entity.ExtensionType;
import com.madrascheck.extension_management.exception.ExtensionLimitExceededException;
import com.madrascheck.extension_management.repository.ExtensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("dev")    // H2 설정을 읽도록
@Transactional
class ExtensionServiceTest {

    @Autowired
    private ExtensionServiceImpl extensionService;

    @Autowired
    private ExtensionRepository extensionRepository;

    @BeforeEach
    void setUp() {
        extensionRepository.deleteAll();
    }

    @Test
    void shouldAddCustomExtensionWhenUnder200Limit() {
        // given: 1~199개의 커스텀 확장자를 미리 저장
        IntStream.rangeClosed(1, 199)
                .forEach(i -> {
                    Extension ext = new Extension("ext" + i, ExtensionType.CUSTOM);
                    extensionRepository.save(ext);
                });

        // when: 200번째 커스텀 확장자 추가
        Extension saved = extensionService.addCustom("newext");

        // then: 저장된 이름이 newext인지, 커스텀 확장자 개수가 200인지 검증
        assertThat(saved.getName()).isEqualTo("newext");
        assertThat(extensionRepository.countByType(ExtensionType.CUSTOM)).isEqualTo(200);
    }

    @Test
    void shouldThrowExceptionWhenExceed200CustomExtensions() {
        // given: 1~200개의 커스텀 확장자를 미리 저장
        IntStream.rangeClosed(1, 200)
                .forEach(i -> {
                    Extension ext = new Extension("ext" + i, ExtensionType.CUSTOM);
                    extensionRepository.save(ext);
                });

        // then: 201번째 추가 시 ExtensionLimitExceededException 예외 발생
        assertThrows(ExtensionLimitExceededException.class,
                () -> extensionService.addCustom("overflow"));
    }
}
