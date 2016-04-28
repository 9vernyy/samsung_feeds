package kz.alisher.samsungnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.utils.Favourite;

/**
 * Created by Alisher Kozhabay on 4/27/2016.
 */
public class FavouriteFragment extends Fragment {

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    public FavouriteFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "FAVOURITE", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }
}
