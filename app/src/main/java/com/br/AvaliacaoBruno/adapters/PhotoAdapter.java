package com.br.AvaliacaoBruno.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.AvaliacaoBruno.R;
import com.br.AvaliacaoBruno.models.Photo;
import com.br.AvaliacaoBruno.util.DownloadImageTask;

import java.util.List;

import com.br.AvaliacaoBruno.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Photo> photos;

    private ItemClickListener itemClickListener;

    // Provide a reference to the views for each data itemClickListener
    // Complex data items may need more than one view per itemClickListener, and
    // you provide access to all the views for a data itemClickListener in a view holder
    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data itemClickListener is just a string in this case
        public ImageView imgPhoto;
        public TextView txtIId;
        public TextView txtITitle;

        public PhotoViewHolder(View v) {
            super(v);
            imgPhoto = v.findViewById(R.id.imgPhoto);
            txtIId = v.findViewById(R.id.txtIId);
            txtITitle = v.findViewById(R.id.txtITitle);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotoAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        new DownloadImageTask(holder.imgPhoto).execute(photo.getThumbnailUrl());
        holder.txtIId.setText(photo.getId().toString());
        holder.txtITitle.setText(photo.getTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}