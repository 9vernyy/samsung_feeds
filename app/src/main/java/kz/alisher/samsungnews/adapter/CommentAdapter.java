package kz.alisher.samsungnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.model.Comment;

/**
 * Created by Alisher Kozhabay on 4/24/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    List<Comment> contents;
    Context ctx;

    public CommentAdapter(List<Comment> contents, Context ctx) {
        this.ctx = ctx;
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment item = contents.get(position);
        holder.author.setText(item.getAuthor());
        holder.createdAt.setText(item.getPubDate());
        holder.content.setText(Html.fromHtml(item.getContent()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView createdAt;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author_comment);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt_comment);
            content = (TextView) itemView.findViewById(R.id.content_comment);
        }
    }
}