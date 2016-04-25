package kz.alisher.samsungnews.rssmanager;

import java.util.List;

/**
 * Created by Alisher Kozhabay on 4/22/2016.
 */
public interface OnRssLoadListener {
    void onSuccess(List<RssItem> rssItems);

    void onFailure(String message);
}
