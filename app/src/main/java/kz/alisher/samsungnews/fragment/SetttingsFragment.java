package kz.alisher.samsungnews.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;


import kz.alisher.samsungnews.R;

/**
 * Created by Adilet on 01.05.2016.
 */
public class SetttingsFragment extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref1);
        final CheckBoxPreference checkBoxPreference = (CheckBoxPreference)findPreference("chb1");
        checkBoxPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (checkBoxPreference.isChecked()) {
                    Log.d("checked", "true");
                } else {
                    Log.d("checked", "false");
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Change theme");
                alertDialogBuilder
                        .setMessage("We need restart application")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent i = getActivity().getPackageManager()
                                        .getLaunchIntentForPackage(getActivity().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    public SetttingsFragment(){

    }

    public static SetttingsFragment newInstance() {
        return new SetttingsFragment();
    }
}
