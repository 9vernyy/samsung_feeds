package kz.alisher.samsungnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.rssmanager.RssItem;

/**
 * Created by Alisher Kozhabay on 4/27/2016.
 */
public class HomeFragment extends Fragment {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (MaterialViewPager) view.findViewById(R.id.materialViewPager);
        toolbar = mViewPager.getToolbar();
        toolbar.setVisibility(View.INVISIBLE);
//        toolbar.setEnabled(false);

//        if (toolbar != null) {
//            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//            final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.setDisplayHomeAsUpEnabled(false);
//                actionBar.setDisplayShowHomeEnabled(false);
//                actionBar.setDisplayShowTitleEnabled(false);
//                actionBar.setDisplayUseLogoEnabled(false);
//                actionBar.setHomeButtonEnabled(false);
//            }
//        }

        mViewPager.getViewPager().setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return new CorporateFragment();
                    case 1:
                        return new ProductsFragment();
                    case 2:
                        return new PressResourcesFragment();
                    case 3:
                        return new ViewsFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "Corporate";
                    case 1:
                        return "Products";
                    case 2:
                        return "Press Resources";
                    case 3:
                        return "Views";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {

            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)
                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getChildCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

//        View logo = view.findViewById(R.id.logo_white);
//        if (logo != null)
//            logo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mViewPager.notifyHeaderChanged();
//                    Toast.makeText(getActivity(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
//                }
//            });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
