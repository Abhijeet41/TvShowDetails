package com.abhi41.tvshowappmvvm.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.adapter.ImageSliderAdapter;
import com.abhi41.tvshowappmvvm.databinding.ActivityTVShowDetailsBinding;
import com.abhi41.tvshowappmvvm.response.TVShowDetailsResponse;
import com.abhi41.tvshowappmvvm.viewmodels.TVShowDetailsViewModel;

import java.util.Locale;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        tvShowDetailsViewModel = new TVShowDetailsViewModel(getApplication());
        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTVShowDetailsBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getTVShowDetails();
    }

    private void getTVShowDetails() {
        String tvShowId = getIntent().getStringExtra("id");
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(this, new Observer<TVShowDetailsResponse>() {
            @Override
            public void onChanged(TVShowDetailsResponse tvShowDetailsResponse) {
                activityTVShowDetailsBinding.setIsLoading(false);
                Toast.makeText(TVShowDetailsActivity.this, tvShowDetailsResponse.getTvShowDetails().getUrl(), Toast.LENGTH_SHORT).show();

                if (tvShowDetailsResponse.getTvShowDetails() != null) {
                    if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                        loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
                    }
                    activityTVShowDetailsBinding.setTvShowImageURL(tvShowDetailsResponse.getTvShowDetails().getImage_path());
                    activityTVShowDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        activityTVShowDetailsBinding.textDescription.setText(Html.fromHtml(
                                tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                HtmlCompat.FROM_HTML_MODE_LEGACY)
                        );
                        activityTVShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                        activityTVShowDetailsBinding.textReadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (activityTVShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                                    activityTVShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                    activityTVShowDetailsBinding.textDescription.setEllipsize(null);
                                    activityTVShowDetailsBinding.textReadMore.setText(getResources().getString(R.string.read_less));
                                } else {
                                    activityTVShowDetailsBinding.textDescription.setMaxLines(4);
                                    activityTVShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                    activityTVShowDetailsBinding.textReadMore.setText(getResources().getString(R.string.read_more));
                                }
                            }
                        });
                        activityTVShowDetailsBinding.setRating(
                                String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating()))
                        );

                    } else {
                        activityTVShowDetailsBinding.textDescription.setText(tvShowDetailsResponse.getTvShowDetails().getDescription());
                    }
                    if (tvShowDetailsResponse.getTvShowDetails().getGenres() != null) {
                        activityTVShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
                    }else {
                        activityTVShowDetailsBinding.setGenre(getResources().getString(R.string.na));
                    }
                    activityTVShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime()+"Min");

                    activityTVShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                    activityTVShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                    activityTVShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                    activityTVShowDetailsBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                            startActivity(intent);
                        }
                    });
                    activityTVShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                    activityTVShowDetailsBinding.buttonEpisode.setVisibility(View.VISIBLE);


                    loadBasicTvShowDetails();
                }
            }
        });
    }

    private void loadImageSlider(String[] sliderImages) {
        activityTVShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTVShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);

        setupSliderIndicators(sliderImages.length);

        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });

    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        // setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTVShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    private void loadBasicTvShowDetails() {
        activityTVShowDetailsBinding.setTvShowName(getIntent().getStringExtra("name"));
        activityTVShowDetailsBinding.setNetworkcountry(getIntent().getStringExtra("network")
                + " (" + getIntent().getStringExtra("country") + ")");

        activityTVShowDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activityTVShowDetailsBinding.setStartedDate(getIntent().getStringExtra("startDate"));
        activityTVShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);


    }

}