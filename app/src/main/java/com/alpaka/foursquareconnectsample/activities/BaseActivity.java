package com.alpaka.foursquareconnectsample.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alpaka.foursquareconnectsample.R;
import com.alpaka.foursquareconnectsample.util.DialogManager;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private Dialog progressDialog;

    protected void addFragment(int containerViewId, Fragment fragment, boolean isBackStack) {
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, boolean isBackStack) {
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void addFragmentAndClearBackStack(int containerViewId, Fragment fragment, boolean isBackStack) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        if (fragmentManager.getBackStackEntryCount() > 1) {
            int fragmentId = fragmentManager.getBackStackEntryAt(1).getId();
            fragmentManager.popBackStack(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void replaceFragmentAndClearBackStack(int containerViewId, Fragment fragment, boolean isBackStack) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        if (fragmentManager.getBackStackEntryCount() > 1) {
            int fragmentId = fragmentManager.getBackStackEntryAt(1).getId();
            fragmentManager.popBackStack(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    protected SharedPreferences getPreferences() {
        if (preferences == null) preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        return preferences;
    }

    protected boolean isVisibleFragment(String fragmentTag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null && fragment.isResumed()) return true;
        else return false;
    }

    @UiThread
    protected void showProgressDialog() {
        if (progressDialog == null)
            progressDialog = DialogManager.getInstance().getProgressDialog(this, R.string.loading);
        progressDialog.show();
    }

    @UiThread
    protected void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @UiThread
    protected void showSimpleDialog(Context context) {
        Dialog dialog = DialogManager.getInstance().getSimpleDialog(context, getString(R.string.question_title), getString(R.string.question_message));
        dialog.show();
    }

}
