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

    public void animateTo(List<RssItem> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<RssItem> newModels) {
        for (int i = contents.size() - 1; i >= 0; i--) {
            final RssItem model = contents.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<RssItem> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final RssItem model = newModels.get(i);
            if (!contents.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<RssItem> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final RssItem model = newModels.get(toPosition);
            final int fromPosition = contents.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public RssItem removeItem(int position) {
        final RssItem model = contents.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, RssItem model) {
        contents.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final RssItem model = contents.remove(fromPosition);
        contents.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
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
