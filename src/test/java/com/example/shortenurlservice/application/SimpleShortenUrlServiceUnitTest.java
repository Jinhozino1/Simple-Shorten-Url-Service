package com.example.shortenurlservice.application;

import com.example.shortenurlservice.domain.*;
import com.example.shortenurlservice.presentation.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimpleShortenUrlServiceUnitTest {

    @Mock
    private ShortenUrlRepository shortenUrlRepository;

    @InjectMocks
    private SimpleShortenUrlService simpleShortenUrlService;

    @Test
    @DisplayName("단축 URL이 계속 중복되면 LackOfShortenUrlKeyException 예외가 발생해야한다.")
    void throwLackOfShortenUrlKeyExceptionTest() {
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(null);

        when(shortenUrlRepository.findShortenUrlByShortenUrlKey(any())).thenReturn(new ShortenUrl(null, null));

        Assertions.assertThrows(LackOfShortenUrlKeyException.class, () -> {
            simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        });
    }

}
