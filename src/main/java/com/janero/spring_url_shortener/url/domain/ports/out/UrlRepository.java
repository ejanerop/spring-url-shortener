package com.janero.spring_url_shortener.url.domain.ports.out;

import com.janero.spring_url_shortener.url.domain.model.Url;

public interface UrlRepository {

    Url save(Url url);

    Url findByKey(String key);

}
