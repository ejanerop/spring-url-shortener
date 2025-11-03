package com.janero.urlshortener.url.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.janero.urlshortener.shared.domain.exception.EncodeException;
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
import com.janero.urlshortener.url.domain.model.Url;
import com.janero.urlshortener.url.domain.ports.in.UrlService;
import com.janero.urlshortener.url.infrastructure.controller.mapper.UrlControllerMapper;
import com.janero.urlshortener.url.infrastructure.controller.request.UrlRequest;
import com.janero.urlshortener.url.infrastructure.controller.response.UrlResponse;
import jakarta.servlet.http.HttpServletResponse;

class UrlControllerTest {

    private UrlService service;
    private UrlControllerMapper mapper;
    private UrlController controller;

    @BeforeEach
    void setUp() {
        service = mock(UrlService.class);
        mapper = mock(UrlControllerMapper.class);
        controller = new UrlController(service, mapper);
    }

    @Test
    void testFindByKeyReturnsUrl() throws KeyNotFoundException {
        String key = "abc123";
        String url = "https://example.com";
        when(service.findUrlByKey(key)).thenReturn(url);

        ResponseEntity<String> response = controller.findByKey(key);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(url, response.getBody());
        verify(service).findUrlByKey(key);
    }

    @Test
    void testRedirectSendsRedirect() throws Exception {
        String key = "abc123";
        String url = "https://example.com";
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(service.findUrlByKey(key)).thenReturn(url);

        controller.redirect(key, response);

        verify(service).findUrlByKey(key);
        verify(response).sendRedirect(url);
    }

    @Test
    void testShortenReturnsShortenedUrl() throws EncodeException {
        String originalUrl = "https://example.com";
        String key = "abc";
        Url model = Url.builder().key(key).url("originalUrl").build();
        UrlRequest request = new UrlRequest();
        request.setUrl(originalUrl);
        when(service.shorten(originalUrl)).thenReturn(model);

        UrlResponse response = new UrlResponse();
        response.setKey(key);
        response.setUrl(originalUrl);
        when(mapper.toResponse(model)).thenReturn(response);

        ResponseEntity<UrlResponse> result = controller.shorten(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(originalUrl, result.getBody().getUrl());
        verify(service).shorten(originalUrl);
        verify(mapper).toResponse(model);
    }

    @Test
    void testDeleteByKeyReturnsNoContent() throws KeyNotFoundException {
        String key = "abc123";

        ResponseEntity<Void> response = controller.deleteByKey(key);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(service).deleteByKey(key);
    }

    @Test
    void testFindByKeyThrowsKeyNotFoundException() throws KeyNotFoundException {
        String key = "notfound";
        when(service.findUrlByKey(key)).thenThrow(new KeyNotFoundException("Not found"));

        assertThrows(KeyNotFoundException.class, () -> controller.findByKey(key));
    }

    @Test
    void testShortenThrowsEncodeException() throws EncodeException {
        UrlRequest request = new UrlRequest();
        request.setUrl("bad-url");
        when(service.shorten("bad-url")).thenThrow(new EncodeException("Encode error"));

        assertThrows(EncodeException.class, () -> controller.shorten(request));
    }

    @Test
    void testDeleteByKeyThrowsKeyNotFoundException() throws KeyNotFoundException {
        String key = "notfound";
        doThrow(new KeyNotFoundException("Not found")).when(service).deleteByKey(key);

        assertThrows(KeyNotFoundException.class, () -> controller.deleteByKey(key));
    }

}