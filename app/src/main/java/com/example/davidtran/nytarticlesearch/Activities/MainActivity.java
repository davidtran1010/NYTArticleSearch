package com.example.davidtran.nytarticlesearch.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.davidtran.nytarticlesearch.Fragments.ArticleFragment;

import com.example.davidtran.nytarticlesearch.Fragments.SFilterFragment;

import com.example.davidtran.nytarticlesearch.Models.Article;
import com.example.davidtran.nytarticlesearch.Models.SearchFilter;
import com.example.davidtran.nytarticlesearch.Models.SearchRequest;
import com.example.davidtran.nytarticlesearch.R;
import com.github.kimkevin.cachepot.CachePot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    SearchRequest searchRequest;
    SearchFilter searchFilter;
    String searchQuery = "";
    @BindView(R.id.searchLoading)
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadArticles();
    }

    private void loadArticles() {

        Fragment fragment = new ArticleFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, fragment).addToBackStack(null).commit();
        loadingBar.setVisibility(View.GONE);
    }

    public void searchArticles(String query, SearchFilter searchFilter) {


        Fragment Articlefragment = new ArticleFragment();
        Bundle bundle = new Bundle();


        bundle.putString("query", query);
        bundle.putParcelable("search_filter", searchFilter);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Articlefragment.setArguments(bundle);
        transaction.replace(R.id.frag_container, Articlefragment).commit();

    }
    private void SearchViewListener(MenuItem itemSearch){
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchQuery = query;

                searchArticles(query, new SearchFilter());
                loadingBar.setVisibility(View.GONE);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadingBar.setVisibility(View.VISIBLE);
                return true;
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);

        SearchViewListener(itemSearch);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.search_filter:
                showSearchFilter();
                Toast.makeText(this, "filter clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reloadAllArticles:
                loadArticles();
                return true;




        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    private void showSearchFilter() {

        Fragment fragment = new SFilterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchquery", searchQuery);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.frag_container2, fragment).addToBackStack(null).commit();


    }

}
