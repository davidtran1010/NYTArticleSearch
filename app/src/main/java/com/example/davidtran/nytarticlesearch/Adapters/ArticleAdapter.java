package com.example.davidtran.nytarticlesearch.Adapters;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.davidtran.nytarticlesearch.Models.Article;
import com.example.davidtran.nytarticlesearch.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtran on 6/23/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Article> articleList;
    Context context;
    static int LOADING = 2;
    static int NORMAL = 1;
    static int NOIMG = 0;


    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = new ArrayList<Article>();
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == LOADING){
            view  = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new loadingItemViewHolder(view);
        }
        else if (viewType == NORMAL) {
            view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
            return new normalViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_article_noimg, parent, false);
            return new noImgViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = articleList.get(position);

        if (holder instanceof normalViewHolder) {
            bindWithNormalImg((normalViewHolder) holder, article);
        } else if (holder instanceof noImgViewHolder) {
            bindWithNoImg((noImgViewHolder) holder, article);
        }
        else if (holder instanceof loadingItemViewHolder) {
            loadingItemViewHolder loadingViewHolder = (loadingItemViewHolder) holder;
            loadingViewHolder.pbLoadingMore.setIndeterminate(true);
        }

    }

    private void bindWithNormalImg(normalViewHolder holder, Article article) {
        int radomColor = ranDomColor();
        int[] rainbow = context.getResources().getIntArray(R.array.colorArray);

        holder.article_snippet.setText(article.getSnipet());
        holder.article_snippet.setBackgroundColor(radomColor);



        Article.Media media = article.getMultimedia().get(0);
        int realWidth = media.getWidth();
        int realHeight = media.getHeight();
        int width = Resources.getSystem().getDisplayMetrics().widthPixels / 2;
        int height = width * realHeight / realWidth;
        ViewGroup.LayoutParams layoutParams = holder.article_image.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;


        Glide.with(context)
                .load(media.getUrl())
                .into(holder.article_image);
    }

    private void bindWithNoImg(noImgViewHolder holder, Article article) {
        Log.i("My log:", article.getSnipet());
        int radomColor = ranDomColor();
        int[] rainbow = context.getResources().getIntArray(R.array.colorArray);


        holder.article_snippet.setText(article.getSnipet());
        holder.article_snippet.setBackgroundColor(radomColor);

    }

    private int ranDomColor() {
        int[] rainbow = context.getResources().getIntArray(R.array.colorArray);
        Random randomGenerator = new Random();
        int randomNumber = 0 + randomGenerator.nextInt(11 - 0);
        return rainbow[randomNumber];
    }

    @Override
    public int getItemViewType(int position) {
        if (hasNoImage(position) == true)
            return NOIMG;
        if(isLoading(position) == true)
            return LOADING;
        return NORMAL;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    private boolean hasNoImage(int position) {
        return articleList.get(position).getMultimedia().isEmpty();
    }
    private boolean isLoading(int position){
        return articleList.get(position) == null;
    }

    static class loadingItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pbLoadingMore)
        ProgressBar pbLoadingMore;

        public loadingItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    static class noImgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_snippet)
        TextView article_snippet;

        public noImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class normalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_image)
        ImageView article_image;
        @BindView(R.id.article_snippet)
        TextView article_snippet;


        public normalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
