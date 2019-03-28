package com.fit.ah.twitter.Search;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.fit.ah.twitter.People.PeopleFragment;
import com.fit.ah.twitter.R;
import com.fit.ah.twitter.SectionsPageAdapter;
import com.fit.ah.twitter.Tweets.TweetsFragment;

public class SearchActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    Toolbar toolbar;
    String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        word = getIntent().getStringExtra("word");

        toolbar = findViewById(R.id.nav_actionbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setTitle("Results for '"+word+"'");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(getResources()
                .getColor(R.color.text_color_secondary), getResources().getColor(R.color.text_color_primary));
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        TweetsFragment fragment1 = new TweetsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("search", true);
        bundle.putString("word", word);
        fragment1.setArguments(bundle);

        PeopleFragment fragment2 = new PeopleFragment();
        fragment2.setArguments(bundle);
        adapter.addFragment(fragment1, "Tweets");
        adapter.addFragment(fragment2, "People");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_color));

        return true;
    }
}
