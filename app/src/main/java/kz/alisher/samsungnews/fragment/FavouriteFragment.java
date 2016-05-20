package kz.alisher.samsungnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.activity.NewsActivity;
import kz.alisher.samsungnews.adapter.NewsRecyclerViewAdapter;
import kz.alisher.samsungnews.rssmanager.RssItem;
import kz.alisher.samsungnews.utils.Favourite;
import kz.alisher.samsungnews.utils.RecyclerItemClickListener;

/**
 * Created by Alisher Kozhabay on 4/27/2016.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<RssItem> rssItems = new ArrayList<>();

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    public FavouriteFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rssItems = Favourite.faList;
        Collections.reverse(rssItems);
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favourite_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new NewsRecyclerViewAdapter(rssItems, getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RssItem clickedItem = rssItems.get(position - 1);
                Intent i = new Intent(getActivity(), NewsActivity.class);
                i.putExtra("item", clickedItem);
                startActivity(i);
            }
        }));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "FAVOURITE", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }
}
