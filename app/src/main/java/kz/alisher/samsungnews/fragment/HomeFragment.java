package kz.alisher.samsungnews.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import kz.alisher.samsungnews.R;

/**
 * Created by Alisher Kozhabay on 4/27/2016.
 */
public class HomeFragment extends Fragment {
    private static final int PAGE_COUNT = 4;
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

        mViewPager.getViewPager().setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return CorporateFragment.newInstance();
                    case 1:
                        return ProductsFragment.newInstance();
                    case 2:
                        return PressResourcesFragment.newInstance();
                    case 3:
                        return ViewsFragment.newInstance();
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
                                "http://img.global.news.samsung.com/global/wp-content/uploads/2015/10/SamsungCSR_Abroad_Main_4.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://img.global.news.samsung.com/global/wp-content/uploads/2016/01/Samsung-Galaxy-Studio_Gear-VR-Theater-with-4d_3.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://img.global.news.samsung.com/global/wp-content/uploads/2016/04/Samsung-Pay_S7-edge_706.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "https://img.global.news.samsung.com/global/wp-content/uploads/2016/04/GalaxyS7_Tutorial_Main_0.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)
                return null;
            }
        });

//        mViewPager.getViewPager().setOffscreenPageLimit(PAGE_COUNT);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        new setAdapterTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private class setAdapterTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mViewPager.getViewPager().setOffscreenPageLimit(PAGE_COUNT);
        }
    }
}