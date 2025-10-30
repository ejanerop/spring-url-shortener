package com.janero.spring_url_shortener.url.infrastructure.controller.request;

import java.net.URI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"validUrl"})
public class UrlRequest {

    @NotBlank
    private String url;

    @AssertTrue
    public boolean isValidUrl() {
        try {
            new URI(url).toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
