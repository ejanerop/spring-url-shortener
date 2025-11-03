package com.janero.urlshortener.url.infrastructure.persistence;

import static com.janero.urlshortener.shared.infrastructure.config.CacheConfig.URL_KEY_CACHE;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
import com.janero.urlshortener.url.domain.model.Url;
import com.janero.urlshortener.url.domain.ports.out.UrlRepository;
import com.janero.urlshortener.url.infrastructure.persistence.jpa.UrlJpaRepository;
import com.janero.urlshortener.url.infrastructure.persistence.jpa.mapper.UrlEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlRepositoryImpl implements UrlRepository {

    private final UrlJpaRepository repo;

    private final UrlEntityMapper mapper;

    @Override
    @Transactional
    public Url save(Url url) {
        return mapper.toModel(repo.save(mapper.toEntity(url)));
    }

    @Override
    public Url findByKey(String key) throws KeyNotFoundException {
        return repo.findById(key).map(mapper::toModel)
                .orElseThrow(() -> new KeyNotFoundException("Key not found"));
    }

    @Override
    public String findUrlByKey(String key) throws KeyNotFoundException {
        return repo.findUrlByKey(key).orElseThrow(() -> new KeyNotFoundException("Key not found"));
    }

    @Override
    public boolean existByKey(String key) {
        return repo.existsById(key);
    }

    @Override
    @Transactional
    @CacheEvict(value = URL_KEY_CACHE)
    public void deleteByKey(String key) {
        repo.deleteById(key);
    }

}
