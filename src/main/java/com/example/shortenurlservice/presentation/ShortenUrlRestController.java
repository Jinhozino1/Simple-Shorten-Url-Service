package com.example.shortenurlservice.presentation;

import com.example.shortenurlservice.application.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.*;

@RestController
public class ShortenUrlRestController {

    private SimpleShortenUrlService simpleShortenUrlService;

    @Autowired
    ShortenUrlRestController(SimpleShortenUrlService simpleShortenUrlService) {
        this.simpleShortenUrlService = simpleShortenUrlService;
    }

    @RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(
            @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto =
                simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        return ResponseEntity.ok(shortenUrlCreateResponseDto);
    }

    @RequestMapping(value = "/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl(
            @PathVariable String shortenUrlKey
    ) throws URISyntaxException {
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        URI redirectUri = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(
            @PathVariable String shortenUrlKey
    ) {
        ShortenUrlInformationDto shortenUrlInformationDto =
                simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);
        return ResponseEntity.ok(shortenUrlInformationDto);
    }

}
