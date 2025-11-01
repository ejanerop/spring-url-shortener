package com.janero.spring_url_shortener.url.infrastructure.persistence;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import com.janero.spring_url_shortener.shared.domain.exception.KeyNotFoundException;
import com.janero.spring_url_shortener.url.domain.model.Url;
import com.janero.spring_url_shortener.url.domain.ports.out.UrlRepository;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.UrlJpaRepository;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.mapper.UrlEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import static com.janero.spring_url_shortener.shared.infrastructure.config.CacheConfig.URL_KEY_CACHE;

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
