package com.app.k2app.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.k2app.R;
import com.app.k2app.activities.ActivityPerfil;
import com.app.k2app.application.MyApplication;
import com.app.k2app.pojo.PojoInReachItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class AdapterInReachItem extends RecyclerView.Adapter<AdapterInReachItem.ViewHolder> {
    private ArrayList<PojoInReachItem> itemsData = new ArrayList<PojoInReachItem>();

    //Universal Image Loader
    private ImageLoader mImageLoader;

    public AdapterInReachItem(ArrayList<PojoInReachItem> itemsData) {
        this.itemsData = itemsData;
        this.mImageLoader = MyApplication.getInstance().getImageLoader();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterInReachItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_inreachitemdata, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        try {
            if (viewHolder != null) {
                PojoInReachItem itemUser = itemsData.get(position);

                if (itemUser.getNome() != null) {
                    viewHolder.txtViewTitle.setText(itemUser.getNome());
                }

                if (itemUser.getImageUrl() != null) {
                    mImageLoader.displayImage(itemUser.getImageUrl(), viewHolder.imgViewIcon);
                    /*
                    mImageLoader.displayImage(itemUser.getImageUrl(), viewHolder.imgViewIcon, null, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            //Log.i(Constants.getTag(), "IN REACH: onLoadingStarted()");
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            Log.i(Constants.getTag(), "IN REACH: onLoadingFailed()" + s);
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            //Log.i(Constants.getTag(), "IN REACH: onLoadingComplete()");
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            Log.i(Constants.getTag(), "IN REACH: onLoadingCancelled()" + s);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String uri, View view, int current, int total) {
                            //Log.i(Constants.getTag(), "onProgressUpdate(" + uri + " : " + total + " : " + current + ")");
                        }
                    });
                    */
                }

                if (itemUser.getDist() != null) {
                    viewHolder.txtViewDist.setText(itemsData.get(position).getDist());
                }

                //if (itemUser.getQtdPhotos() != null) {
                viewHolder.txtViewQtdPhotos.setText(Integer.toString(itemsData.get(position).getQtdPhotos()));
                //}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //Log.i(Constants.getTag(), "IN REACH Total de Users: " + itemsData.size());
        return itemsData.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    // *** retirei o static
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;
        public TextView txtViewDist;
        public TextView txtViewQtdPhotos;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.tvPostUsername);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.ivPostUserImage);
            txtViewDist = (TextView) itemLayoutView.findViewById(R.id.item_dist);
            txtViewQtdPhotos = (TextView) itemLayoutView.findViewById(R.id.item_qtdPhotos);

            //implementando o click na imagem
            imgViewIcon.setOnClickListener(this);
            txtViewQtdPhotos.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == imgViewIcon) {
                clickNome(view, getPosition());
            }
        }
    }

    // METODOS ONCLICKLISTENER
    public void clickNome(View v, Integer i) {
        Intent activityPerfil = new Intent(v.getContext(), ActivityPerfil.class);
        activityPerfil.putExtra("userId", itemsData.get(i).getId());
        v.getContext().startActivity(activityPerfil);
    }
}
