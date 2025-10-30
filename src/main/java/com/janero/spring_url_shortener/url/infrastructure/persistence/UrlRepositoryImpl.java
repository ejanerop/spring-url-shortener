package com.janero.spring_url_shortener.url.infrastructure.persistence;

import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;
import com.janero.spring_url_shortener.url.domain.model.Url;
import com.janero.spring_url_shortener.url.domain.ports.out.UrlRepository;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.UrlJpaRepository;
import com.janero.spring_url_shortener.url.infrastructure.persistence.jpa.mapper.UrlEntityMapper;
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
    public Url findByKey(String key) {
        return repo.findById(key).map(mapper::toModel).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String findUrlByKey(String key) {
        return repo.findUrlByKey(key).orElseThrow(NoSuchElementException::new);
    }

}
