package kz.alisher.samsungnews.utils;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Adilet on 30.04.2016.
 */
public class GlobalItems extends AsyncTask<String, Integer, Elements> {
    private static final String URL = "http://news.samsung.com/global/feed";
    private Elements mItems;

    @Override
    protected Elements doInBackground(String... params) {
        try {
            Document rssDocument = Jsoup.connect(URL).parser(Parser.xmlParser()).get();
            mItems = rssDocument.select("item");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mItems;
    }
}
