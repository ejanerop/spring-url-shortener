package com.janero.spring_url_shortener.url.domain.ports.in;

import com.janero.spring_url_shortener.url.domain.model.Url;

public interface UrlService {

    Url shorten(String url);

    Url findByKey(String key);

}
