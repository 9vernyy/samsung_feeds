package kz.alisher.samsungnews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.activity.SubCategoryActivity;
import kz.alisher.samsungnews.adapter.CategoryAdapter;
import kz.alisher.samsungnews.utils.RecyclerItemClickListener;

/**
 * Created by Adilet on 29.04.2016.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<List<String>> catMap = new ArrayList<>();

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    public CategoryFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Log.d("DATA", catMap.toString());
        mAdapter = new RecyclerViewMaterialAdapter(new CategoryAdapter(catMap, getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), SubCategoryActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        }));
    }

    private void fillData() {
        catMap.add(Arrays.asList("All", ""));
        catMap.add(Arrays.asList("Corporate", "5"));
        catMap.add(Arrays.asList("Products", "8"));
        catMap.add(Arrays.asList("Press Resources", "7"));
        catMap.add(Arrays.asList("Views", "1"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}
