package com.mtit.gitso.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mtit.gitso.R;
import com.mtit.gitso.model.IssueListModel;

import java.util.ArrayList;

/**
 * Created by mtit on 5/18/2017.
 */

public class CollectionAdaptorIssues extends RecyclerView.Adapter<CollectionAdaptorIssues.CollectionViewHolder> {
    ArrayList<IssueListModel> data;
    private final CollectionAdaptorIssues.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(IssueListModel item);
    }

    public CollectionAdaptorIssues(ArrayList<IssueListModel> items, CollectionAdaptorIssues.OnItemClickListener listener) {
        this.data = items;
        this.listener = listener;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaptor_row_collection_issues,parent,false);

        CollectionAdaptorIssues.CollectionViewHolder viewHolder = new CollectionAdaptorIssues.CollectionViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CollectionAdaptorIssues.CollectionViewHolder holder, int position) {
        IssueListModel listModel = data.get(position);
        holder.bind(listModel,listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder{
        static boolean state;
        protected TextView title,createdate,status,body;
        protected LinearLayout expandedArea;
        public CollectionViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.issue_title);
            createdate = (TextView) itemView.findViewById(R.id.created_date);
            status = (TextView) itemView.findViewById(R.id.status);
            expandedArea = (LinearLayout) itemView.findViewById(R.id.expand_area);
            body = (TextView) expandedArea.findViewById(R.id.body);
        }

        public void bind(final IssueListModel testModel, final CollectionAdaptorIssues.OnItemClickListener listener){
            title.setText(testModel.getTitle());
            createdate.setText(testModel.getCreatedDate());
            status.setText(String.valueOf(testModel.getStatus()));
            body.setText(testModel.getBody());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(testModel);
                    expandedArea.setVisibility(!CollectionViewHolder.state ? View.VISIBLE : View.GONE);
                    CollectionViewHolder.state = !CollectionViewHolder.state;
                }
            });
        }
    }
}
