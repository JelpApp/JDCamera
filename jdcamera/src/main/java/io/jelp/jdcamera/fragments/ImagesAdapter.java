package io.jelp.jdcamera.fragments;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import io.jelp.jdcamera.R;

/**
 * Created by angel on 2/27/18.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    Context context;
    List<String> images;
    ImagesAdapter.OnItemClickListener onItemClickListener;

    public ImagesAdapter(Context context, List<String> images,ImagesAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.images = images;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImagesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        final File file = new File(images.get(position));
        Glide.with(context)
                .load(file)
                .into(holder.imgPicture);

        holder.imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemImageClicked(file);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPicture;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            imgPicture = (ImageView) itemView.findViewById(R.id.imgPicture);
        }
    }

    public interface OnItemClickListener {
        void onItemImageClicked(File file);
    }

}
