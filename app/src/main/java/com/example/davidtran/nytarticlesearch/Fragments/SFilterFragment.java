package com.example.davidtran.nytarticlesearch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.davidtran.nytarticlesearch.Models.SearchFilter;
import com.example.davidtran.nytarticlesearch.R;
import com.github.kimkevin.cachepot.CachePot;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtran on 6/24/17.
 */

public class SFilterFragment extends Fragment {
    @BindView(R.id.btnApplyFilter)
    Button btnApplyFilter;
    @BindView(R.id.btnCloseFilter)
    Button btnCloseFilter;
    @BindView(R.id.bgDatePicker)
    DatePicker bgDatePicker;
    @BindView(R.id.spOrder)
    Spinner spOrder;
    @BindView(R.id.cbArt)
    CheckBox cbArt;
    @BindView(R.id.cbSport)
    CheckBox cbSport;
    @BindView(R.id.cbFashion)
    CheckBox cbFashion;

    String query;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_searchfilter, container, false);
        ButterKnife.bind(this, view);

        query = getArguments().getString("searchquery");
        setupListener();
        return view;
    }

    private void setupListener() {
        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BeginDate = "";
                if (bgDatePicker.getMonth() < 10) {
                    BeginDate = "" + bgDatePicker.getYear() + "0" + bgDatePicker.getMonth() + bgDatePicker.getDayOfMonth();
                } else {
                    BeginDate = "" + bgDatePicker.getYear() + bgDatePicker.getMonth() + bgDatePicker.getDayOfMonth();
                }
                String Order = spOrder.getSelectedItem().toString();
                boolean hasArt = cbArt.isChecked();
                boolean hasSport = cbSport.isChecked();
                boolean hasFashion = cbFashion.isChecked();

                SearchFilter searchFilter = new SearchFilter(BeginDate, Order, hasArt, hasSport, hasFashion);
                //Bundle bundle = new Bundle();
                //bundle.putParcelable("search_filter",searchFilter);

                //Intent intent = new Intent(getActivity(), MainActivity.class).putExtras(bundle);


                Fragment articleFragment = new ArticleFragment();

                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                bundle.putParcelable("searchfilter", searchFilter);
                articleFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frag_container, articleFragment).addToBackStack(null).commit();


                //hideFilterFragment();


            }
        });
        btnCloseFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Close filter", Toast.LENGTH_SHORT).show();
                hideFilterFragment();
            }
        });

    }
    private void hideFilterFragment(){
        Fragment fragment = new Fragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container2, fragment).commit();
    }

}
