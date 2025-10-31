package com.janero.spring_url_shortener.url.domain.ports.out;

import com.janero.spring_url_shortener.url.domain.model.Url;

public interface UrlRepository {

    Url save(Url url);

    Url findByKey(String key);

    String findUrlByKey(String key);

    boolean existByKey(String key);

    void deleteByKey(String key);

}
