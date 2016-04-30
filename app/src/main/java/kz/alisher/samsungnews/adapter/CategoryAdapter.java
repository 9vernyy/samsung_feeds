package kz.alisher.samsungnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.alisher.samsungnews.R;

/**
 * Created by Adilet on 29.04.2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<List<String>> contents;
    Context ctx;

    public CategoryAdapter(List<List<String>> contents, Context ctx) {
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
                .inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String> cat = contents.get(position);
        holder.title.setText(cat.get(0));
        holder.countSubs.setText(cat.get(1));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView countSubs;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_cat);
            countSubs = (TextView) itemView.findViewById(R.id.countSubs);
        }
    }
}
