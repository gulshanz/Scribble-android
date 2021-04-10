package com.itl.scribble.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itl.scribble.interfaces.ListItemClickListener;
import com.itl.scribble.OldNotesObj;
import com.itl.scribble.R;

import java.util.List;

public class OldPostAdapter extends RecyclerView.Adapter<OldPostAdapter.ViewHolder>  {
    List<OldNotesObj> dataList;
    Context mContext;
    ListItemClickListener mListener;

    public OldPostAdapter(List<OldNotesObj> postsList, Context context, ListItemClickListener listItemClickListener) {
        dataList = postsList;
        mContext=context;
        mListener=listItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem=layoutInflater.inflate(R.layout.old_scribbles_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getCreated_on());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.dateCard);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onListItemClickListener(getAdapterPosition(),dataList.get(getAdapterPosition()));
        }
    }
}
