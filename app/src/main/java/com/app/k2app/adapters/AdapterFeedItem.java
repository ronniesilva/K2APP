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
import android.widget.Toast;

import com.app.k2app.R;
import com.app.k2app.activities.ActivityPerfil;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.pojo.PojoFeedItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.ArrayList;


public class AdapterFeedItem extends RecyclerView.Adapter<AdapterFeedItem.ViewHolder> {

    private ArrayList<PojoFeedItem> itemsData = new ArrayList<PojoFeedItem>();

    //Universal Image Loader
    private ImageLoader mImageLoader;

    public AdapterFeedItem(ArrayList<PojoFeedItem> itemsData) {
        this.itemsData = itemsData;

        this.mImageLoader = MyApplication.getInstance().getImageLoader();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterFeedItem.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_feeditemdata, null);

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
                PojoFeedItem itemPost = itemsData.get(position);

                // Imagem do usuario que postou
                if (!itemPost.getUserImage().equals("null")) {

                    String URIPostUserImage = MyApplication.getWsUrlPhotoPerfil()+ "/" + itemPost.getUserImage();
                    mImageLoader.displayImage(URIPostUserImage, viewHolder.ivPostUserImage, null, new ImageLoadingListener() {
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
                            //Log.i(Config.TAG, "PostUserImagem onProgressUpdate(" + uri + " : " + total + " : " + current + ")");
                        }
                    });
                }

                // Nome do usuario que postou
                if (!itemPost.getShowName().equals("null")) {
                    String nomeParts = itemPost.getShowName();
                    String[] nParts = nomeParts.split("\\ ");
                    String postUsername;
                    if (nParts[0].length() >= 15) { //testa se nome tem mais do que 15 caracteres
                        postUsername = nParts[0].substring(0, 14); //mostra apenas 15 caracteres
                    } else {
                        postUsername = nParts[0]; // nome menor do que 15 caracteres, mostra nome completo
                    }
                    viewHolder.tvPostUsername.setText(postUsername);
                }

                // Distancia do usuario que postou
                if (!itemPost.getDist().equals("null")) {
                    String file = itemPost.getDist();
                    String[] dParts = file.split("\\.");
                    String postUserDist;
                    if (dParts[0].equals("0")) { // Menor que 1.000m
                        if (dParts[1].substring(0, 1).equals("0")) {
                            if (dParts[1].substring(1, 2).equals("0")) {
                                postUserDist = dParts[1].substring(2, 3) + " m";
                            } else {
                                postUserDist = dParts[1].substring(1, 3) + " m";
                            }
                        } else {
                            postUserDist = dParts[1].substring(0, 3) + " m";
                        }
                    } else if (dParts[0].length() > 1) { // Maior que 9.999m
                        postUserDist = dParts[0] + " km";
                    } else { // Entre 1.000m e 9.999m
                        postUserDist = dParts[0] + "." + dParts[1].substring(0, 3) + " m";
                    }

                    viewHolder.tvPostUserDist.setText(postUserDist);
                }

                // Tempo do usuario que postou
                if (itemPost.getOldTime() != null) {
                    Integer oldTime = itemPost.getOldTime();
                    String postUserOldTime;
                    if (oldTime < 60) {
                        postUserOldTime = oldTime + " min";
                    } else if (oldTime >= 60 && oldTime < 1440) {
                        postUserOldTime = oldTime / 60 + " h";
                    } else {
                        postUserOldTime = ((oldTime / 60) / 24) + " d";
                    }

                    viewHolder.tvPostUserOldTime.setText(postUserOldTime);
                }

                // Imagem do Post
                //Log.i(Constants.TAG, "FEED Adapter: Imagem - " + itemPost.getPostImageUrl());
                if (!itemPost.getImageUrl().equals("null")) {
                    String URIPostImage =  MyApplication.getWsUrlPostImages() + "/" + itemPost.getImageUrl();

                    mImageLoader.displayImage(URIPostImage, viewHolder.ivPostImage);


                    /*
                    String URIPostImage = Constants.getServerAPPURL() + "/" + Constants.WsDirPostImage + "/" + itemPost.getPostImageUrl();
                    mImageLoader.displayImage(URIPostImage, viewHolder.ivPostImage, null, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                            //Log.i(Constants.TAG, "POST Imagem onLoadingStarted()");
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            Log.i(Constants.TAG, "POST Imagem onLoadingFailed(): " + s);
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            //Log.i(Constants.TAG, "POST Imagem onLoadingComplete()");
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                            Log.i(Constants.TAG, "POST Imagem onLoadingCancelled() : " + s);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String uri, View view, int current, int total) {
                            //Log.i(Constants.TAG, "POST Imagem onProgressUpdate(" + uri + " : " + total + " : " + current + ")");
                        }
                    });

                    */
                }

                if (!itemPost.getText().equals("null")) {
                    viewHolder.tvPostText.setText(itemPost.getText());
                }

                if (itemPost.getQtdLike() != null) {
                    viewHolder.tvPostLikeNum.setText(Integer.toString(itemPost.getQtdLike()));
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

        public ImageView ivPostUserImage;
        public TextView tvPostUsername;
        public TextView tvPostUserDist;
        public TextView tvPostUserOldTime;
        public ImageView ivPostUserComplaint;
        public ImageView ivPostImage;
        public TextView tvPostText;
        public ImageView ivPostLike;
        public TextView tvPostLikeNum;
        public ImageView ivReply;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ivPostUserImage = (ImageView) itemLayoutView.findViewById(R.id.ivPostUserImage);
            tvPostUsername = (TextView) itemLayoutView.findViewById(R.id.tvPostUsername);
            tvPostUserDist = (TextView) itemLayoutView.findViewById(R.id.tvPostUserDist);
            tvPostUserOldTime = (TextView) itemLayoutView.findViewById(R.id.tvPostUserOldTime);
            ivPostUserComplaint = (ImageView) itemLayoutView.findViewById(R.id.ivPostUserComplaint);
            ivPostImage = (ImageView) itemLayoutView.findViewById(R.id.ivPostImage);
            tvPostText = (TextView) itemLayoutView.findViewById(R.id.tvPostText);
            ivPostLike = (ImageView) itemLayoutView.findViewById(R.id.ivPostLike);
            tvPostLikeNum = (TextView) itemLayoutView.findViewById(R.id.tvPostLikeNum);
            ivReply = (ImageView) itemLayoutView.findViewById(R.id.ivReply);

            //implementando o click na imagem
            ivPostUserImage.setOnClickListener(this);
            ivPostUserComplaint.setOnClickListener(this);
            ivPostImage.setOnClickListener(this);
            ivPostLike.setOnClickListener(this);
            ivReply.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view == ivPostUserImage) {
                clickUserImage(view, getPosition());
            } else if (view == ivPostImage) {
                //clickPostImage(view, getPosition());
            } else if (view == ivPostUserComplaint) {
                clickComplaint(view, getPosition());
            } else if (view == ivPostLike) {
                clickLike(view, getPosition());
            } else if (view == ivReply) {
                clickReply(view, getPosition());
            }
        }
    }

    // METODOS ONCLICKLISTENER
    public void clickUserImage(View v, Integer i) {

        Intent activityPerfil = new Intent(v.getContext(), ActivityPerfil.class);
        activityPerfil.putExtra("userId", itemsData.get(i).getId());
        v.getContext().startActivity(activityPerfil);
        //Toast.makeText(v.getContext(), "Nome: " + itemsData.get(i).getId(), Toast.LENGTH_SHORT).show();
    }

    /*
    public void clickPostImage(View v, Integer i) {
        Toast.makeText(v.getContext(), "Nome: " + itemsData.get(i).getPostUsername(), Toast.LENGTH_SHORT).show();
        Log.i(Config.TAG, "clicou na imagem");
    }
    */

    public void clickComplaint(View v, Integer i) {
        Toast.makeText(v.getContext(), "Nome: " + itemsData.get(i).getQtdLike(), Toast.LENGTH_SHORT).show();
    }

    public void clickLike(View v, Integer i) {
        Toast.makeText(v.getContext(), "Nome: " + itemsData.get(i).getDist(), Toast.LENGTH_SHORT).show();
    }

    public void clickReply(View v, Integer i) {
        Toast.makeText(v.getContext(), "Nome: " + itemsData.get(i).getOldTime(), Toast.LENGTH_SHORT).show();
    }
}
