package com.janero.spring_url_shortener.url.application.service;

import org.springframework.stereotype.Service;
import com.janero.spring_url_shortener.url.domain.model.Url;
import com.janero.spring_url_shortener.url.domain.ports.in.UrlService;
import com.janero.spring_url_shortener.url.domain.ports.out.UrlRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    
    private final UrlRepository repo;
    
    @Override
    public Url shorten(String url) {
        //TODO: implement
        throw new UnsupportedOperationException("Unimplemented method 'shorten'");
    }
    
    @Override
    public Url findByKey(String key) {
        //TODO: implement
        throw new UnsupportedOperationException("Unimplemented method 'findByKey'");
    }
    
    @Override
    public String findUrlByKey(String key) {
        return repo.findUrlByKey(key);
    }
    
}
