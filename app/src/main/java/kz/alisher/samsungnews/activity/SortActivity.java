package kz.alisher.samsungnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.adapter.CategoryAdapter;
import kz.alisher.samsungnews.adapter.NewsRecyclerViewAdapter;
import kz.alisher.samsungnews.rssmanager.OnRssLoadListener;
import kz.alisher.samsungnews.rssmanager.RssItem;
import kz.alisher.samsungnews.rssmanager.RssReader;
import kz.alisher.samsungnews.utils.RecyclerItemClickListener;

/**
 * Created by Adilet on 29.04.2016.
 */
public class SortActivity extends AppCompatActivity implements OnRssLoadListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<RssItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("title");
        loadFeeds(chooseCat(title));
        mRecyclerView = (RecyclerView) findViewById(R.id.sort_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                RssItem clickedItem = mItems.get(position - 1);
                Intent i = new Intent(SortActivity.this, NewsActivity.class);
                i.putExtra("item", clickedItem);
                startActivity(i);
            }
        }));
    }


    private void loadFeeds(String url) {
        String[] urlArr = {url};

        new RssReader(this)
                .showDialog(true)
                .urls(urlArr)
                .parse(this);
    }

    @Override
    public void onSuccess(List<RssItem> rssItems) {
        for (RssItem rssItem : rssItems) {
            mItems.add(rssItem);
        }
        mAdapter = new RecyclerViewMaterialAdapter(new NewsRecyclerViewAdapter(mItems, this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Error:\n" + message, Toast.LENGTH_SHORT).show();
    }

    private String chooseCat(String title) {
        String str = "";

        switch (title) {

            case "All":
                str = "http://news.samsung.com/global/feed";
                break;
            case "People and culture":
                str = "http://news.samsung.com/global/category/corporate/people-culture/feed";
                break;
            case "Citizenship":
                str = "http://news.samsung.com/global/category/corporate/citizenship/feed";
                break;
            case "Techonology":
                str = "http://news.samsung.com/global/category/corporate/technology/feed";
                break;
            case "Design":
                str = "http://news.samsung.com/global/category/corporate/design/feed";
                break;
            case "Others":
                str = "http://news.samsung.com/global/category/corporate/corporateothers/feed";
                break;
            case "Mobile":
                str = "http://news.samsung.com/global/category/products/mobile/feed";
                break;
            case "TV and audio":
                str = "http://news.samsung.com/global/category/products/tv-audio/feed";
                break;
            case "Home appliances":
                str = "http://news.samsung.com/global/category/products/home-appliances/feed";
                break;
            case "Camera and computing":
                str = "http://news.samsung.com/global/category/products/camera-computing/feed";
                break;
            case "Printing solutions":
                str = "http://news.samsung.com/global/category/products/printing-solutions/feed";
                break;
            case "B2B":
                str = "http://news.samsung.com/global/category/products/b2b/feed";
                break;
            case "Semiconductors":
                str = "http://news.samsung.com/global/category/products/semiconductors/feed";
                break;
            case "Others ":
                str = "http://news.samsung.com/global/category/products/products-others/feed";
                break;
            case "Press release":
                str = "http://news.samsung.com/global/category/press-resources/press-release/feed";
                break;
            case "Issues and facts":
                str = "http://news.samsung.com/global/category/press-resources/issuesfacts/feed";
                break;
            case "Statements":
                str = "http://news.samsung.com/global/category/press-resources/issuesfacts/statements/feed";
                break;
            case "FAQS":
                str = "http://news.samsung.com/global/category/press-resources/issuesfacts/faqs/feed";
                break;
            case "Video":
                str = "http://news.samsung.com/global/category/press-resources/video/feed";
                break;
            case "Photo":
                str = "http://news.samsung.com/global/category/press-resources/photo/feed";
                break;
            case "Infographics":
                str = "http://news.samsung.com/global/category/press-resources/infographics/feed";
                break;
            case "Views":
                str = "http://news.samsung.com/global/category/views/view/feed";
                break;

        }
        return str;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
