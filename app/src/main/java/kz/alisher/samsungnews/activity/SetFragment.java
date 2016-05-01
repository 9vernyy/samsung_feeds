package kz.alisher.samsungnews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.fragment.SetttingsFragment;

/**
 * Created by Adilet on 01.05.2016.
 */
public class SetFragment extends Fragment {
    public static SetFragment newInstance() {
        SetFragment fragment = new SetFragment();
        return fragment;
    }

    public SetFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.flSetContent, SetttingsFragment.newInstance()).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_set, container, false);
    }
}
