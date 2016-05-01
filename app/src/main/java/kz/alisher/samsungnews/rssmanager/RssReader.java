package kz.alisher.samsungnews.rssmanager;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kz.alisher.samsungnews.model.Comment;


/**
 * Created by Alisher Kozhabay on 4/22/2016.
 */
public class RssReader implements OnFeedLoadListener {
    private static final String COMMENT = "wfw:commentRss";
    private static final String NUMBER_OF_COMMENTS = "slash:comments";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String CONTENT = "content:encoded";
    private static final String CREATED_AT = "pubDate";

    private String[] mUrlList;
    private boolean mShowDialog = true;
    private Context mContext;
    private List<RssItem> mRssItems = new ArrayList<>();
    private RssParser mRssParser;
    private int mPosition = 0;
    private MaterialDialog mMaterialDialog;
    private OnRssLoadListener mOnRssLoadListener;

    public RssReader(Context context) {
        this.mContext = context;
    }

    public RssReader urls(String[] urls) {
        this.mUrlList = urls;
        return this;
    }

    public RssReader showDialog(boolean status) {
        this.mShowDialog = status;
        return this;
    }

    public void parse(OnRssLoadListener onRssLoadListener) {
        this.mOnRssLoadListener = onRssLoadListener;

        if (mShowDialog) {
            initDialog();
        }

        if (mRssItems != null) {
            mRssItems.clear();
        }

        if (mUrlList != null) {
            parseRss(0);
        } else {
            throw new NullPointerException("Url list cannot be empty");
        }
    }

    private void initDialog() {
        mMaterialDialog = new MaterialDialog.Builder(mContext)
                .title("Loading")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false)
                .negativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        if (mRssParser != null) {
                            mRssParser.cancel(true);
                        }
                        mOnRssLoadListener.onFailure("User performed dismiss action");
                    }
                })
                .build();
        mMaterialDialog.show();

        mMaterialDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mRssParser != null) {
                    mRssParser.cancel(true);
                }
                mOnRssLoadListener.onFailure("User performed dismiss action");
            }
        });
    }

    private void parseRss(int position) {
        if (position != mUrlList.length) {
            mRssParser = new RssParser(mUrlList[position], this);
            mRssParser.execute();
            String source = getWebsiteName(mUrlList[position]);
            if (mMaterialDialog != null) {
                mMaterialDialog.setContent(source);
            }
        } else {
            if (mMaterialDialog != null) {
                mMaterialDialog.dismiss();
            }
            mOnRssLoadListener.onSuccess(mRssItems);
        }
    }

    private String getWebsiteName(String url) {
        URI uri;
        try {
            uri = new URI(url);
            return uri.getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public void onSuccess(Elements items) {
        for (Element item : items) {
            mRssItems.add(getRssItem(item));
        }
        mPosition++;
        parseRss(mPosition);
    }

    @Override
    public void onFailure(String message) {
        mOnRssLoadListener.onFailure(message);
    }

    private RssItem getRssItem(Element element) {
        RssItem rssItem = new RssItem();
        String title = element.select(TITLE).first().text();
        String description = element.select(DESCRIPTION).first().text();
        String content = element.getElementsByTag(CONTENT).text();
        String createdAt = element.select(CREATED_AT).first().text();
        String corrDateAndTime = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            SimpleDateFormat df2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
            corrDateAndTime = df2.format(format.parse(createdAt));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String comment = element.getElementsByTag(COMMENT).text();
        String numberOfComments = element.getElementsByTag(NUMBER_OF_COMMENTS).text();
        String img;
        if (Jsoup.parse(content).getElementsByTag("img").first() == null) {
            img = "http://img.global.news.samsung.com/image/default_image.png";
        } else {
            img = Jsoup.parse(content).getElementsByTag("img").first().attr("src");
        }

        rssItem.setTitle(title);
        rssItem.setImg(img);
        rssItem.setDescription(description);
        rssItem.setContent(content);
        rssItem.setCreatedAt(corrDateAndTime);
        rssItem.setCommentUrl(comment);
        rssItem.setNumberOfComments(numberOfComments);
        rssItem.setIsFavourite(false);

        return rssItem;
    }
}