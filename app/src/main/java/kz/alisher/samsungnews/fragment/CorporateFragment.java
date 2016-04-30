package kz.alisher.samsungnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import kz.alisher.samsungnews.activity.NewsActivity;
import kz.alisher.samsungnews.adapter.NewsRecyclerViewAdapter;
import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.rssmanager.OnRssLoadListener;
import kz.alisher.samsungnews.rssmanager.RssItem;
import kz.alisher.samsungnews.rssmanager.RssReader;
import kz.alisher.samsungnews.utils.EndlessRecyclerOnScrollListener;
import kz.alisher.samsungnews.utils.RecyclerItemClickListener;

/**
 * Created by Alisher Kozhabay on 4/23/2016.
 */
public class CorporateFragment extends Fragment implements OnRssLoadListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<RssItem> mContentItems = new ArrayList<>();

    public static CorporateFragment newInstance() {
        return new CorporateFragment();
    }

    public CorporateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CORPORATE", "ACTIVITY");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {

            @Override
            public void onLoadMore(int current_page) {

            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RssItem clickedItem = mContentItems.get(position - 1);
                Intent i = new Intent(getActivity(), NewsActivity.class);
                i.putExtra("item", clickedItem);
                startActivity(i);
            }
        }));

        loadFeeds();
    }

    private void loadFeeds() {
        String[] urlArr = {"http://news.samsung.com/global/category/corporate/feed"};

        new RssReader(getActivity())
                .showDialog(true)
                .urls(urlArr)
                .parse(this);
    }

    @Override
    public void onSuccess(List<RssItem> rssItems) {
        for (RssItem rssItem : rssItems) {
            mContentItems.add(rssItem);
        }
        mAdapter = new RecyclerViewMaterialAdapter(new NewsRecyclerViewAdapter(mContentItems, getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getActivity(), "Error:\n" + message, Toast.LENGTH_SHORT).show();
    }
}