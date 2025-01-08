package io.timpac.youtubebatch.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class YoutubeItem {
    private String kind;
    private String etag;
    private Id id;
    private Snippet snippet;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public static class Id {
        private String kind;
        private String videoId;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }

    static public class Snippet {
        private String publishedAt;
        private String channelId;
        private String title;
        private String description;
        private String channelTitle;
        private String publishTime;
        private Thumbnails thumbnails;

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public void setChannelTitle(String channelTitle) {
            this.channelTitle = channelTitle;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public Thumbnails getThumbnails() {
            return Objects.requireNonNullElseGet(thumbnails, Thumbnails::new);
        }

        public void setThumbnails(Thumbnails thumbnails) {
            this.thumbnails = thumbnails;
        }

        public static class Thumbnails {
            @JsonProperty("default")
            private Thumbnail _default;
            private Thumbnail medium;
            private Thumbnail high;

            public Thumbnail get_default() {
                return Objects.requireNonNullElseGet(_default, Thumbnail::new);
            }

            public void set_default(Thumbnail _default) {
                this._default = _default;
            }

            public Thumbnail getMedium() {
                return Objects.requireNonNullElseGet(medium, Thumbnail::new);
            }

            public void setMedium(Thumbnail medium) {
                this.medium = medium;
            }

            public Thumbnail getHigh() {
                return Objects.requireNonNullElseGet(high, Thumbnail::new);
            }

            public void setHigh(Thumbnail high) {
                this.high = high;
            }

            public static class Thumbnail {
                private String url;
                private int width;
                private int height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }
        }
    }
}
