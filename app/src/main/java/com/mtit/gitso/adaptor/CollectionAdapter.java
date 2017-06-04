package com.mtit.gitso.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mtit.gitso.R;
import com.mtit.gitso.model.RepoListModel;

import java.util.ArrayList;

/**
 * Created by mtit on 5/18/2017.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {
    ArrayList<RepoListModel> data;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(RepoListModel item);
    }

   /* public CollectionAdapter(ArrayList<RepoListModel> data) {
        this.data = data;
    }*/

    public CollectionAdapter(ArrayList<RepoListModel> items, OnItemClickListener listener) {
        this.data = items;
        this.listener = listener;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptor_row_collection,parent,false);

        CollectionViewHolder viewHolder = new CollectionViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        RepoListModel listModel = data.get(position);
        holder.bind(listModel,listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder{
        protected TextView repo_name,description,issue_count;
        protected ImageView image;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            repo_name = (TextView) itemView.findViewById(R.id.repo_name);
            description = (TextView) itemView.findViewById(R.id.description);
            issue_count = (TextView) itemView.findViewById(R.id.issue_count);
            image = (ImageView) itemView.findViewById(R.id.repo_img);
        }

        public void bind(final RepoListModel testModel, final OnItemClickListener listener){
            repo_name.setText(testModel.getName());
            description.setText(testModel.getDescription());
            issue_count.setText(String.valueOf(testModel.getIssueCount()));
            if(testModel.getLanguage().equalsIgnoreCase("C#")){
                image.setImageResource(R.drawable.c);
            }else if(testModel.getLanguage().equalsIgnoreCase("java")){
                image.setImageResource(R.drawable.java);
            }else if(testModel.getLanguage().equalsIgnoreCase("python")){
                image.setImageResource(R.drawable.py);
            }else if(testModel.getLanguage().equalsIgnoreCase("c++")){
                image.setImageResource(R.drawable.cplus);
            }else if(testModel.getLanguage().equalsIgnoreCase("lua")){
                image.setImageResource(R.drawable.lua);
            }else if(testModel.getLanguage().equalsIgnoreCase("php")){
                image.setImageResource(R.drawable.php);
            }else if(testModel.getLanguage().equalsIgnoreCase("ruby")){
                image.setImageResource(R.drawable.ruby);
            }else if(testModel.getLanguage().equalsIgnoreCase("shell")){
                image.setImageResource(R.drawable.shell);
            }else{
                image.setImageResource(R.drawable.logo);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(testModel);
                }
            });
        }
    }
}
