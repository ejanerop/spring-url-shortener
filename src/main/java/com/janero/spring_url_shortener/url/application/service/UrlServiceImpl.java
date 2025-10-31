package com.janero.spring_url_shortener.url.application.service;

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

        if (key.isEmpty())
            throw new EncodeException("Can't generate key");
        Url toInsert = new Url();
        toInsert.setKey(key);
        toInsert.setUrl(url);
        return repo.save(toInsert);
    }

    @Override
    public Url findByKey(String key) {
        return repo.findByKey(key);
    }

    @Override
    @Cacheable(value = URL_KEY_CACHE, keyGenerator = "keyGenerator")
    public String findUrlByKey(String key) {
        return repo.findUrlByKey(key);
    }

    @Override
    public void deleteByKey(String key) throws KeyNotFoundException {
        if (!repo.existByKey(key))
            throw new KeyNotFoundException("Key not found");
        repo.deleteByKey(key);
    }

}
