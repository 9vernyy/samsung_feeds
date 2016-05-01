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

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.adapter.CategoryAdapter;
import kz.alisher.samsungnews.utils.RecyclerItemClickListener;

/**
 * Created by Adilet on 29.04.2016.
 */
public class SubCategoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<List<String>> catMap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int pos = getIntent().getIntExtra("id", 0);
        fillData(pos);
        mRecyclerView = (RecyclerView) findViewById(R.id.subcategory_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        Log.d("DATA", catMap.toString());
        mAdapter = new RecyclerViewMaterialAdapter(new CategoryAdapter(catMap, this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(SubCategoryActivity.this, SortActivity.class);
                Log.d("POSITION", catMap.get(position).get(0));
                i.putExtra("title", catMap.get(position).get(0));
                Log.d("TESTOVICH", catMap.get(position).get(0));
                startActivity(i);
            }
        }));
    }

    private void fillData(int pos) {
        switch (pos){
            case 0:
                Log.d("LOG", pos + "");
                break;
            case 1:
                break;
            case 2:
                catMap.add(Arrays.asList("People and culture", ""));
                catMap.add(Arrays.asList("Citizenship",""));
                catMap.add(Arrays.asList("Techonology", ""));
                catMap.add(Arrays.asList("Design", ""));
                catMap.add(Arrays.asList("Others", ""));
                break;
            case 3:
                catMap.add(Arrays.asList("Mobile", ""));
                catMap.add(Arrays.asList("TV and audio", ""));
                catMap.add(Arrays.asList("Home appliances", ""));
                catMap.add(Arrays.asList("Camera and computing", ""));
                catMap.add(Arrays.asList("Printing solutions", ""));
                catMap.add(Arrays.asList("B2B", ""));
                catMap.add(Arrays.asList("Semiconductors", ""));
                catMap.add(Arrays.asList("Others ", ""));
                break;
            case 4:
                catMap.add(Arrays.asList("Press release", ""));
                catMap.add(Arrays.asList("Issues and facts", ""));
                catMap.add(Arrays.asList("Statements", ""));
                catMap.add(Arrays.asList("FAQS", ""));
                catMap.add(Arrays.asList("Video", ""));
                catMap.add(Arrays.asList("Photo", ""));
                catMap.add(Arrays.asList("Infographics", ""));
                break;
            case 5:
                catMap.add(Arrays.asList("Views", ""));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
