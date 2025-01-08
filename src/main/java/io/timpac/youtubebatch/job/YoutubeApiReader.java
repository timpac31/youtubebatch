package io.timpac.youtubebatch.job;

import io.timpac.youtubebatch.domain.Youtube;
import io.timpac.youtubebatch.domain.YoutubeApi;
import io.timpac.youtubebatch.domain.YoutubeItem;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class YoutubeApiReader implements ItemReader<YoutubeItem> {
    private final RestTemplate restTemplate;
    private final YoutubeApi youtubeApi;
    private Iterator<YoutubeItem> iterator;
    private final DateTimeFormatter dateFormat;

    public YoutubeApiReader(RestTemplateBuilder restTemplateBuilder, YoutubeApi youtubeApi) {
        this.restTemplate = restTemplateBuilder.build();
        this.youtubeApi = youtubeApi;
        dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'");
    }

    @Override
    public YoutubeItem read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator == null) {
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            String uri = UriComponentsBuilder.fromUriString(youtubeApi.getUrl())
                    .queryParam("key", youtubeApi.getKey())
                    .queryParam("part", "snippet")
                    .queryParam("q", "양천구")
                    .queryParam("type", "video")
                    .queryParam("publishedBefore", today.format(dateFormat))
                    .queryParam("publishedAfter", yesterday.format(dateFormat))
                    .queryParam("maxResults", 30)
                    .build().toUriString();

            Youtube response = restTemplate.getForObject(uri, Youtube.class);
            if (response == null) return null;
            iterator = response.getItems().iterator();
        }

        if (iterator.hasNext())  {
            return iterator.next();
        } else {
            return null;
        }
    }
}
