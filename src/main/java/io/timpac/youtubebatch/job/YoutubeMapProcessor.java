package io.timpac.youtubebatch.job;

import io.timpac.youtubebatch.domain.YoutubeItem;
import io.timpac.youtubebatch.domain.YoutubeWriteItem;
import org.springframework.batch.item.ItemProcessor;

public class YoutubeMapProcessor implements ItemProcessor<YoutubeItem, YoutubeWriteItem> {

    @Override
    public YoutubeWriteItem process(YoutubeItem item) throws Exception {
        YoutubeWriteItem writeItem = new YoutubeWriteItem();
        String videoId = item.getId().getVideoId();
        YoutubeItem.Snippet snippet = item.getSnippet();

        writeItem.setVideoId(videoId);
        writeItem.setTitle(snippet.getTitle());
        writeItem.setDescription(snippet.getDescription());
        writeItem.setChannelId(snippet.getChannelId());
        writeItem.setChannelTitle(snippet.getChannelTitle());
        writeItem.setPublishedAt(snippet.getPublishedAt());
        writeItem.setThumbnailUrl(snippet.getThumbnails().getMedium().getUrl());

        return writeItem;
    }
}
