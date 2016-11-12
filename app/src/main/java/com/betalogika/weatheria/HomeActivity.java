package com.betalogika.weatheria;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.betalogika.weatheria.databinding.ActivityHomeBinding;

import java.util.ArrayList;

interface HomeActivityCallback {
    void updateResource(ArrayList<Forecast> listOfForecast, City city);
}

public class HomeActivity extends AppCompatActivity implements HomeActivityCallback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mProgressView;
    private LinearLayout mLinearLayout;
    private ArrayList<Forecast> listOfForecast = new ArrayList<>();

    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setupComponent();
    }

    private void setupComponent() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_home);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HomeAdapter(this.listOfForecast);
        mRecyclerView.setAdapter(mAdapter);

        mProgressView = findViewById(R.id.home_progress);
        setupHeader();
        getResource();
    }

    private void setupHeader(){
        activityHomeBinding.setCity(new City(0,0,"-","-"));
    }

    private void getResource(){
        showProgress(true);
        new ForecastWeatherAPIClient("Jakarta", this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mLinearLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void updateResource(ArrayList<Forecast> listOfForecast, City city){
        activityHomeBinding.setCity(city);
        this.listOfForecast = listOfForecast;
        mAdapter            = new HomeAdapter(this.listOfForecast);
        mRecyclerView.setAdapter(mAdapter);
        showProgress(false);
        animate();
    }

    private void animate() {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mLinearLayout.getContext(), R.anim.fadein);
        mLinearLayout.setAnimation(animAnticipateOvershoot);
    }
}
