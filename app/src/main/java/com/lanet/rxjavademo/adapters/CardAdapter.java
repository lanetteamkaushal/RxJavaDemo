package com.lanet.rxjavademo.adapters;

/**
 * Created For LaNet Team
 * by lcom75 on 22/6/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanet.rxjavademo.R;
import com.lanet.rxjavademo.apis.Github;
import com.lanet.rxjavademo.helper.Item;
import com.lanet.rxjavademo.helper.ItemTouchHelperAdapter;
import com.lanet.rxjavademo.helper.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    public interface OnStartDragListener {

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }


    List<Github> mItems;
    private final OnStartDragListener dragStartListener;

    public CardAdapter(OnStartDragListener dragStartListener) {
        super();
        this.dragStartListener = dragStartListener;
        mItems = new ArrayList<Github>();
    }

    public void addData(Github github) {
        mItems.add(github);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.relativeReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
        Github github = mItems.get(i);
        viewHolder.login.setText(github.getLogin());
        viewHolder.repos.setText("repos: " + github.getPublicRepos());
        viewHolder.blog.setText("blog: " + github.getBlog());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public boolean removeItemOnSwipe(int position) {
        boolean success = false;
        if (position < mItems.size()) {
            success = mItems.remove(position) != null;
        }
        return success;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        final Item item = new Item();
        item.setItemName(mItems.get(position).getLogin());
        notifyItemRemoved(position);
        mItems.remove(position);
        notifyItemRangeChanged(0, getItemCount());
//        tvNumber.setText(String.valueOf(itemList.size()));

//        final Snackbar snackbar =  Snackbar
//
//                .make(tvNumber,context.getResources().getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
//                .setActionTextColor(ContextCompat.getColor(context, R.color.white))
//                .setAction(context.getResources().getString(R.string.item_undo), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        itemList.add(position, item);
//                        notifyItemInserted(position);
//                        tvNumber.setText(String.valueOf(itemList.size()));
//
//                    }
//                });
//
//
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
//        TextView tvSnack = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//        TextView tvSnackAction = (TextView) snackbar.getView().findViewById( android.support.design.R.id.snackbar_action );
//        tvSnack.setTextColor(Color.WHITE);
//        tvSnack.setTypeface(Typefaces.getRobotoMedium(context));
//        tvSnackAction.setTypeface(Typefaces.getRobotoMedium(context));
//        snackbar.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public TextView login;
        public TextView repos;
        public TextView blog;
        public RelativeLayout relativeReorder;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
            relativeReorder = (RelativeLayout) itemView.findViewById(R.id.relativeReorder);
        }

        @Override
        public void onItemSelected(Context context) {
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
//            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.white));
//            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onItemClear(Context context) {
            itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
//            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.textlight), PorterDuff.Mode.SRC_IN);
//            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.textlight));
        }
    }
}
