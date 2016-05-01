package kz.alisher.samsungnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.adapter.NewsRecyclerViewAdapter;
import kz.alisher.samsungnews.rssmanager.RssItem;
import kz.alisher.samsungnews.utils.GlobalItems;
import kz.alisher.samsungnews.utils.RecyclerItemClickListener;

/**
 * Created by Alisher Kozhabay on 4/24/2016.
 */
public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String COMMENT = "wfw:commentRss";
    private static final String NUMBER_OF_COMMENTS = "slash:comments";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String CONTENT = "content:encoded";
    private static final String CREATED_AT = "pubDate";

    private SearchView searchView;
    private NewsRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<RssItem> items = new ArrayList<>();
    private List<RssItem> items2 = new ArrayList<>();
    private List<RssItem> filteredModelList = new ArrayList<>();
    private TextView mNoReminderView;
    private TextView mNoReminderView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNoReminderView = (TextView) findViewById(R.id.no_reminder_text);
        mNoReminderView1 = (TextView) findViewById(R.id.no_reminder_text1);
        setSupportActionBar(toolbar);
        setTitle("Samsung news");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NewsRecyclerViewAdapter(items2, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RssItem clickedItem = filteredModelList.get(position);
                Intent i = new Intent(SearchActivity.this, NewsActivity.class);
                i.putExtra("item", clickedItem);
                startActivity(i);
            }
        }));
        setItems();

        if (filteredModelList.isEmpty()){
            mNoReminderView.setVisibility(View.VISIBLE);
            mNoReminderView1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search");
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<RssItem> filter(List<RssItem> models, String query) {
        query = query.toLowerCase();

        filteredModelList = new ArrayList<>();
        for (RssItem model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void setItems() {
        try {
            Elements elements = new GlobalItems().execute().get();
            for (Element e : elements) {
                items.add(getRssItem(e));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private RssItem getRssItem(Element element) {
        RssItem rssItem = new RssItem();
        String title = element.select(TITLE).first().text();
        String description = element.select(DESCRIPTION).first().text();
        String content = element.getElementsByTag(CONTENT).text();
        String createdAt = element.select(CREATED_AT).first().text();
        String corrDateAndTime="";
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            SimpleDateFormat df2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");
            corrDateAndTime = df2.format(format.parse(createdAt));
        }
        catch (java.text.ParseException e)
        {
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<RssItem> filteredModelList = filter(items, newText);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        if (filteredModelList.isEmpty()){
            mNoReminderView.setVisibility(View.VISIBLE);
            mNoReminderView1.setVisibility(View.VISIBLE);
        } else {
            mNoReminderView.setVisibility(View.INVISIBLE);
            mNoReminderView1.setVisibility(View.INVISIBLE);
        }
        return true;
    }
}
