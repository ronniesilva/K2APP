package com.app.k2app.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.k2app.R;
import com.app.k2app.activities.ActivityPerfil;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.pojo.PojoContactsItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.ArrayList;


public class AdapterContactsItem extends RecyclerView.Adapter<AdapterContactsItem.ViewHolder> {

    private ArrayList<PojoContactsItem> itemsData = new ArrayList<PojoContactsItem>();

    //Universal Image Loader
    private ImageLoader mImageLoader;

    public AdapterContactsItem(ArrayList<PojoContactsItem> itemsData) {
        this.itemsData = itemsData;

        if (mImageLoader == null){
            this.mImageLoader = MyApplication.getInstance().getImageLoader();
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterContactsItem.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_contactsitemdata, null);

        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        try {

            if (viewHolder != null) {
                // - get data from your itemsData at this position
                // - replace the contents of the view with that itemsData

                // captura o objeto
                PojoContactsItem itemContact = itemsData.get(position);

                // Imagem do contato
                if (!itemContact.getImageUrl().equals("null")) {

                    String URIContactImage = MyApplication.getWsUrlPhotoPerfil() + "/" + itemContact.getImageUrl();
                    mImageLoader.displayImage(URIContactImage, viewHolder.ivContactUserImage, null, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            //Log.i(Constants.TAG, "PostUserImagem onLoadingStarted()");
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            Log.i(Config.TAG, "PostUserImagem onLoadingFailed() :" + s);
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            // Log.i(Constants.TAG, "PostUserImagem onLoadingComplete()");
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            Log.i(Config.TAG, "PostUserImagem onLoadingCancelled() :" + s);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String uri, View view, int current, int total) {
                            Log.i(Config.TAG, "PostUserImagem onProgressUpdate(" + uri + " : " + total + " : " + current + ")");
                        }
                    });
                }

                // Nome do usuario que postou
                if (!itemContact.getShowName().equals("null")) {
                    viewHolder.tvContactUsername.setText(itemContact.getShowName());
                }

                if (!itemContact.getMsgStatus().equals("null")) {
                    viewHolder.tvContactMsgStatus.setText(itemContact.getMsgStatus());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //Log.i(Constants.TAG, "FEED Total de Posts: " + itemsData.size());
        return itemsData.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivContactUserImage;
        public TextView tvContactUsername;
        public TextView tvContactMsgStatus;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ivContactUserImage = (ImageView) itemLayoutView.findViewById(R.id.ivContactUserImage);
            tvContactUsername = (TextView) itemLayoutView.findViewById(R.id.tvContactUsername);
            tvContactMsgStatus = (TextView) itemLayoutView.findViewById(R.id.tvContactMsgStatus);

            //implementando o click na imagem
            ivContactUserImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view == ivContactUserImage) {
                clickUserImage(view, getPosition());
            }
        }
    }

    // METODOS ONCLICKLISTENER
    public void clickUserImage(View v, Integer i) {
        Intent activityPerfil = new Intent(v.getContext(), ActivityPerfil.class);
        activityPerfil.putExtra("userId", itemsData.get(i).getId());
        v.getContext().startActivity(activityPerfil);
    }
}
