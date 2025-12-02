
package com.WebScrapping.WebScrapping.service;

import com.WebScrapping.WebScrapping.model.ScrapeResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapeService {

    public ScrapeResponse WebScrape(String url) {
        ScrapeResponse response = new ScrapeResponse();

        try {
            Connection.Response res = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                            " AppleWebKit/537.36 (KHTML, like Gecko)" +
                            " Chrome/123.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com/")
                    .followRedirects(true)
                    .ignoreHttpErrors(true)   // important
                    .ignoreContentType(true)
                    .timeout(15000)
                    .execute();

            int status = res.statusCode();
            System.out.println("Status: " + status);

            if (status >= 400) {
                System.out.println("Blocked or error by site: " + url);
                return response;  // return empty JSON instead of crashing
            }

            Document doc = res.parse();

            List<String> images = new ArrayList<>();
            List<String> videos = new ArrayList<>();
            List<String> links = new ArrayList<>();

            for (Element img : doc.select("img")) {
                String src = img.absUrl("src");
                if (!src.isEmpty()) images.add(src);
            }

//            for (Element video : doc.select("video")) {
//                String src = video.absUrl("src");
//                if (!src.isEmpty()) videos.add(src);
//            }
            for (Element video : doc.select("video[src]")) {
                String src = video.absUrl("src");
                if (!src.isEmpty()) videos.add(src);
            }

            for (Element source : doc.select("video source[src]")) {
                String src = source.absUrl("src");
                if (!src.isEmpty()) videos.add(src);
            }

            for (Element iframe : doc.select("iframe[src]")) {
                String src = iframe.absUrl("src");
                if (src.contains("youtube") || src.contains("vimeo")) {
                    videos.add(src);
                }
            }

            for (Element link : doc.select("a[href]")) {
                String href = link.absUrl("href");
                if (!href.isEmpty()) links.add(href);
            }

            response.setImages(images);
            response.setVideos(videos);
            response.setLinks(links);

            return response;

        } catch (Exception e) {
            System.out.println("Scrape failed: " + e.getMessage());
            return response;
        }
    }
}
