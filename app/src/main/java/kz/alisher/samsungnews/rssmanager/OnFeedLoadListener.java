package kz.alisher.samsungnews.rssmanager;

import org.jsoup.select.Elements;

/**
 * Created by Alisher Kozhabay on 4/22/2016.
 */
public interface OnFeedLoadListener {
    void onSuccess(Elements elements);

    void onFailure(String message);
}
