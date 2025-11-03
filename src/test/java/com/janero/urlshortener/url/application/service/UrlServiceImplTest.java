package com.janero.urlshortener.url.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.janero.urlshortener.shared.domain.exception.EncodeException;
import com.janero.urlshortener.url.application.util.Base64Encoder;
import com.janero.urlshortener.url.domain.model.Url;
import com.janero.urlshortener.url.domain.ports.out.UrlRepository;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlRepository repo;

    @InjectMocks
    private UrlServiceImpl urlService;

    @Test
    void shorten_ShouldCreateAndSaveUrl() throws EncodeException {
        // Arrange
        String originalUrl = "http://example.com";
        String encodedKey = Base64Encoder.encode(originalUrl);
        Url expectedUrl = Url.builder().key(encodedKey).url(originalUrl).build();

        when(repo.existByKey(anyString())).thenReturn(false);
        when(repo.save(any(Url.class))).thenReturn(expectedUrl);

        // Act
        Url result = urlService.shorten(originalUrl);

        assertNotNull(result);
        assertEquals(originalUrl, result.getUrl());
        verify(repo, atLeastOnce()).existByKey(anyString());
        verify(repo).save(any(Url.class));
    }

    @Test
    void shorten_WhenKeyAlreadyExists_ShouldThrowEncodeException() throws EncodeException {
        // Arrange
        String originalUrl = "http://example.com";
        Base64Encoder.encode(originalUrl);

        when(repo.existByKey(any())).thenReturn(true);

        // Act & Assert
        assertThrows(EncodeException.class, () -> urlService.shorten(originalUrl));
        verify(repo, times(7)).existByKey(any()); // 5 retries + 1 final check
    }
}
