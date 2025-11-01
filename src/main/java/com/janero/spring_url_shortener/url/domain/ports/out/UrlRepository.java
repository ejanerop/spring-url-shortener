package com.janero.spring_url_shortener.url.domain.ports.out;

import com.janero.spring_url_shortener.shared.domain.exception.KeyNotFoundException;
import com.janero.spring_url_shortener.url.domain.model.Url;

public interface UrlRepository {

    Url save(Url url);

    Url findByKey(String key) throws KeyNotFoundException;

    String findUrlByKey(String key) throws KeyNotFoundException;

    boolean existByKey(String key);

    void deleteByKey(String key);

}
