package kz.alisher.samsungnews.model;

/**
 * Created by Alisher Kozhabay on 4/24/2016.
 */
public class Comment {
    private String author;
    private String pubDate;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
