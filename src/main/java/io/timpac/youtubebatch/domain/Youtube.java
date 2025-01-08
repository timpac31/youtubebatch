package io.timpac.youtubebatch.domain;

import java.util.List;

public class Youtube {
    private List<YoutubeItem> items;

    public List<YoutubeItem> getItems() {
        return items;
    }

    public void setItems(List<YoutubeItem> items) {
        this.items = items;
    }
}
