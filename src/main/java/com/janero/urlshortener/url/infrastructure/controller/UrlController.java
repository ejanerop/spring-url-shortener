package com.janero.urlshortener.url.infrastructure.controller;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.janero.urlshortener.shared.domain.exception.EncodeException;
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
import com.janero.urlshortener.url.domain.ports.in.UrlService;
import com.janero.urlshortener.url.infrastructure.controller.mapper.UrlControllerMapper;
import com.janero.urlshortener.url.infrastructure.controller.request.UrlRequest;
import com.janero.urlshortener.url.infrastructure.controller.response.UrlResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/url")
@Tag(name = "Url", description = "Controller to manage URL shorten endpoints")
public class UrlController {

    private final UrlService service;

    private final UrlControllerMapper mapper;

    @GetMapping("/{key}")
    public ResponseEntity<String> findByKey(@PathVariable String key) throws KeyNotFoundException {
        return ResponseEntity.ok().body(service.findUrlByKey(key));
    }

    @GetMapping("/{key}/redirect")
    public void redirect(@PathVariable String key, HttpServletResponse response)
            throws KeyNotFoundException, IOException {
        String url = service.findUrlByKey(key);
        response.sendRedirect(url);
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shorten(@RequestBody @Validated UrlRequest request)
            throws EncodeException {
        return ResponseEntity.ok().body(mapper.toResponse(service.shorten(request.getUrl())));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteByKey(@PathVariable String key) throws KeyNotFoundException {
        service.deleteByKey(key);
        return ResponseEntity.noContent().build();
    }

}
