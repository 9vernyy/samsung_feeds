package kz.alisher.samsungnews.rssmanager;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kz.alisher.samsungnews.model.Comment;

/**
 * Created by Alisher Kozhabay on 4/24/2016.
 */
public class RssComments extends AsyncTask<String, Integer, List<Comment>> {
    private Elements mItems;
    private String mUrl;
    private List<Comment> comments = new ArrayList<>();

    public RssComments(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    protected List<Comment> doInBackground(String... params) {
        try {
            Document rssDocument = Jsoup.connect(mUrl).parser(Parser.xmlParser()).get();
            mItems = rssDocument.select("item");
            if (mItems.size() > 0) {
                for (Element e : mItems) {
                    Comment comment1 = new Comment();
                    comment1.setAuthor(e.select("title").first().text());
                    comment1.setContent(e.getElementsByTag("content:encoded").text());
                    String pubDate = e.select("pubDate").first().text();
                    String corrDateAndTime = "";
                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
                    SimpleDateFormat df2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
                    corrDateAndTime = df2.format(format.parse(pubDate));
                    comment1.setPubDate(corrDateAndTime);
                    comments.add(comment1);

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
