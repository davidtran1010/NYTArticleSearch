package com.example.davidtran.nytarticlesearch.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.davidtran.nytarticlesearch.Models.Article;
import com.example.davidtran.nytarticlesearch.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtran on 6/23/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Article> articleList;
    Context context;
    static int NORMAL = 1;
    static int NOIMG = 0;

    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = new ArrayList<Article>();
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
            return new normalViewHolder(view);
        }
        else  {
            View view = LayoutInflater.from(context).inflate(R.layout.item_article_noimg,parent,false);
            return new noImgViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = articleList.get(position);

        if (holder instanceof normalViewHolder){
            bindWithNormalImg((normalViewHolder) holder,article);
        }
        else if(holder instanceof noImgViewHolder){
            bindWithNoImg((noImgViewHolder) holder,article);
        }


    }
    private void bindWithNormalImg(normalViewHolder holder, Article article){

        holder.article_snippet.setText(article.getSnipet());
        Glide.with(context)
                .load(article.getMultimedia().get(0).getUrl())
                .into(holder.article_image);
    }
    private void bindWithNoImg(noImgViewHolder holder, Article article){
        Log.i("My log:",article.getSnipet());
        holder.article_snippet.setText(article.getSnipet());

    }


    @Override
    public int getItemViewType(int position) {
        if (hasNoImage(position) == true)
                return NOIMG;
        return NORMAL;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
    private boolean hasNoImage(int position){
        return articleList.get(position).getMultimedia().isEmpty();
    }
    static class noImgViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.article_snippet)
        TextView article_snippet;

        public noImgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
     static class normalViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.article_image)
        ImageView article_image;
        @BindView(R.id.article_snippet)
        TextView article_snippet;

        public normalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
