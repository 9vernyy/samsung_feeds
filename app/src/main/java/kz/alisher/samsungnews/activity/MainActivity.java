package kz.alisher.samsungnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.fragment.CorporateFragment;
import kz.alisher.samsungnews.fragment.FavouriteFragment;
import kz.alisher.samsungnews.fragment.HomeFragment;
import kz.alisher.samsungnews.fragment.PressResourcesFragment;
import kz.alisher.samsungnews.fragment.ProductsFragment;
import kz.alisher.samsungnews.fragment.ViewsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

//        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
//        toolbar = mViewPager.getToolbar();
//
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            final ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                actionBar.setDisplayShowHomeEnabled(true);
//                actionBar.setDisplayShowTitleEnabled(true);
//                actionBar.setDisplayUseLogoEnabled(false);
//                actionBar.setHomeButtonEnabled(true);
//            }
//        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mViewPager.getViewPager().setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                switch (position % 4) {
//                    case 0:
//                        return CorporateFragment.newInstance();
//                    case 1:
//                        return ProductsFragment.newInstance();
//                    case 2:
//                        return PressResourcesFragment.newInstance();
//                    case 3:
//                        return ViewsFragment.newInstance();
//                    default:
//                        return null;
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return 4;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                switch (position % 4) {
//                    case 0:
//                        return "Corporate";
//                    case 1:
//                        return "Products";
//                    case 2:
//                        return "Press Resources";
//                    case 3:
//                        return "Views";
//                }
//                return "";
//            }
//        });
//
//        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
//
//            @Override
//            public HeaderDesign getHeaderDesign(int page) {
//                switch (page) {
//                    case 0:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.green,
//                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
//                    case 1:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.blue,
//                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
//                    case 2:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.cyan,
//                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
//                    case 3:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.red,
//                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
//                }
//
//                //execute others actions if needed (ex : modify your header logo)
//                return null;
//            }
//        });
//
//        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
//        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
//
//        View logo = findViewById(R.id.logo_white);
//        if (logo != null)
//            logo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mViewPager.notifyHeaderChanged();
//                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
//                }
//            });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.favourite) {
            fragment = FavouriteFragment.newInstance();
        } else if (id == R.id.homeF) {
            fragment = HomeFragment.newInstance();
        }
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item) || toggle.onOptionsItemSelected(item);
    }
}