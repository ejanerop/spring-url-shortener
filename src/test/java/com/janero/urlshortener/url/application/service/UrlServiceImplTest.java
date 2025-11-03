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
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
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
        String originalUrl = "http://example.com";
        String encodedKey = Base64Encoder.encode(originalUrl);
        Url expectedUrl = Url.builder().key(encodedKey).url(originalUrl).build();

        when(repo.existByKey(anyString())).thenReturn(false);
        when(repo.save(any(Url.class))).thenReturn(expectedUrl);

        Url result = urlService.shorten(originalUrl);

        assertNotNull(result);
        assertEquals(originalUrl, result.getUrl());
        verify(repo, atLeastOnce()).existByKey(anyString());
        verify(repo).save(any(Url.class));
    }

    @Test
    void shorten_WhenKeyAlreadyExists_ShouldThrowEncodeException() throws EncodeException {
        String originalUrl = "http://example.com";
        Base64Encoder.encode(originalUrl);

        when(repo.existByKey(any())).thenReturn(true);

        assertThrows(EncodeException.class, () -> urlService.shorten(originalUrl));
        verify(repo, times(7)).existByKey(any()); // 5 retries + 1 final check
    }

    @Test
    void deleteByKey_ShouldDelete_WhenKeyExists() throws KeyNotFoundException {
        String key = "abc123";
        when(repo.existByKey(key)).thenReturn(true);

        urlService.deleteByKey(key);

        verify(repo).existByKey(key);
        verify(repo).deleteByKey(key);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void deleteByKey_ShouldThrowException_WhenKeyDoesNotExist() {
        String key = "nonexistent";
        when(repo.existByKey(key)).thenReturn(false);

        KeyNotFoundException ex =
                assertThrows(KeyNotFoundException.class, () -> urlService.deleteByKey(key));

        assertEquals("Key not found", ex.getMessage());
        verify(repo).existByKey(key);
        verify(repo, never()).deleteByKey(anyString());
    }

}
