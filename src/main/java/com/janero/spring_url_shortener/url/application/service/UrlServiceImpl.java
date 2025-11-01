package com.janero.spring_url_shortener.url.application.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.janero.spring_url_shortener.shared.domain.exception.EncodeException;
import com.janero.spring_url_shortener.shared.domain.exception.KeyNotFoundException;
import com.janero.spring_url_shortener.url.application.util.Base64Encoder;
import com.janero.spring_url_shortener.url.domain.model.Url;
import com.janero.spring_url_shortener.url.domain.ports.in.UrlService;
import com.janero.spring_url_shortener.url.domain.ports.out.UrlRepository;
import lombok.RequiredArgsConstructor;

import static com.janero.spring_url_shortener.shared.infrastructure.config.CacheConfig.URL_KEY_CACHE;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository repo;

    private static final int MAX_RETRIES = 5;

    @Override
    public Url shorten(String url) throws EncodeException {
        String key;
        int retries = 0;

        do {
            key = Base64Encoder.encode(url);
            retries++;
        } while (repo.existByKey(key) && retries <= MAX_RETRIES);

        if (repo.existByKey(key))
            throw new EncodeException("Can't generate key");

        return repo.save(Url.builder().key(key).url(url).build());
    }

    @Override
    public Url findByKey(String key) throws KeyNotFoundException {
        return repo.findByKey(key);
    }

    @Override
    @Cacheable(value = URL_KEY_CACHE, keyGenerator = "keyGenerator")
    public String findUrlByKey(String key) throws KeyNotFoundException {
        return repo.findUrlByKey(key);
    }

    @Override
    @CacheEvict(value = URL_KEY_CACHE)
    public void deleteByKey(String key) throws KeyNotFoundException {
        if (!repo.existByKey(key))
            throw new KeyNotFoundException("Key not found");
        repo.deleteByKey(key);
    }

}
