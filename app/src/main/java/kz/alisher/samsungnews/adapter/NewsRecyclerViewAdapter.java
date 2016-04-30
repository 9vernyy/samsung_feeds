package kz.alisher.samsungnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.rssmanager.RssItem;

/**
 * Created by Alisher Kozhabay on 4/23/2016.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    List<RssItem> contents;
    Context ctx;

    public NewsRecyclerViewAdapter(List<RssItem> contents, Context ctx) {
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
                .inflate(R.layout.list_item_card_small, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RssItem item = contents.get(position);
        holder.title.setText(item.getTitle());
        holder.createdAt.setText(item.getCreatedAt());
        Picasso.with(holder.thumbnail.getContext()).load(item.getImg()).into(holder.thumbnail);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView createdAt;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleNews);
            createdAt = (TextView) itemView.findViewById(R.id.createdAt);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
