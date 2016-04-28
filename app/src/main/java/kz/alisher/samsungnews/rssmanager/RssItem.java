package kz.alisher.samsungnews.rssmanager;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import kz.alisher.samsungnews.model.Comment;

/**
 * Created by Alisher Kozhabay on 4/22/2016.
 */
public class RssItem implements Serializable{
    private String title;
    private String description;
    private String content;
    private String img;
    private String createdAt;
    private List<Comment> comments;
    private String commentUrl;
    private String numberOfComments;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public String getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(String numberOfComments) {
        this.numberOfComments = numberOfComments;
    }
}
