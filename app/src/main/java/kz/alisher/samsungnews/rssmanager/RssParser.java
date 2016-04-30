package kz.alisher.samsungnews.rssmanager;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Alisher Kozhabay on 4/22/2016.
 */
public class RssParser extends AsyncTask<String, Integer, String> {
    private Elements mItems;
    private String mUrl;
    private OnFeedLoadListener mOnFeedLoadListener;

    public RssParser(String url, OnFeedLoadListener onFeedLoadListener) {
        this.mUrl = url;
        this.mOnFeedLoadListener = onFeedLoadListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Document rssDocument = Jsoup.connect(mUrl).parser(Parser.xmlParser()).get();
            mItems = rssDocument.select("item");
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("success")) {
            mOnFeedLoadListener.onSuccess(mItems);
        } else if (s.equals("failure")) {
            mOnFeedLoadListener.onFailure("Failed to parse the url\n" + mUrl);
        }
    }
}
