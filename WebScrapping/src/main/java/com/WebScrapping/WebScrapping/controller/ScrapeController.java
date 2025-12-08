package com.WebScrapping.WebScrapping.controller;

import com.WebScrapping.WebScrapping.model.ScrapeResponse;
import com.WebScrapping.WebScrapping.service.ScrapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
public class ScrapeController {
    @Autowired
    private ScrapeService scrapeService;

    @GetMapping("/api/scrape")
    public ScrapeResponse scrape(@RequestParam String url) throws IOException {
        return scrapeService.WebScrape(url);
    }

//    @GetMapping("/")
//    public String home() {
//        return "Web Scraper is running. Use /api/scrape?url=...";
//    }

    @GetMapping("/status")
    public String status() {
        return "Web Scraper is running. Use /api/scrape?url=...";
    }

}
