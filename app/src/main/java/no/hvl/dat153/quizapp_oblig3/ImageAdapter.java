package no.hvl.dat153.quizapp_oblig3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<ImageEntity> imageList;

    public ImageAdapter(Context context, List<ImageEntity> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageEntity imageEntity = imageList.get(position);
        holder.textView.setText(imageEntity.getImageDescription());
        holder.imageView.setImageURI(imageEntity.getImageUri());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setList(List<ImageEntity> image) {
        this.imageList = image;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_gallery);
            textView = itemView.findViewById(R.id.text_view_name);
        }
    }
}

