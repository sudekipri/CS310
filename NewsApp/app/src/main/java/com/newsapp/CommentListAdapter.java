package com.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newsapp.request.comments.Comment;
import com.newsapp.request.comments.Item;


public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentHolder> {

    private Comment comment;

    public CommentListAdapter(Comment comment) {
        this.comment = comment;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Item commentItem = comment.getItems().get(position);
        holder.setData(commentItem);
    }

    @Override
    public int getItemCount() {
        if (comment != null) {
            return comment.getItems().size();
        } else {
            return 0;
        }
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView comment, commentUserName;
        View itemView;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.comments_item_imageView);
            comment = itemView.findViewById(R.id.commentText);
            commentUserName = itemView.findViewById(R.id.user_name);
            this.itemView = itemView;
        }

        public void setData(Item commentItem) {
            commentUserName.setText(commentItem.getName());
            imageView.setImageResource(R.drawable.person);
            this.comment.setText(commentItem.getText());
        }

    }
}
