package com.app.k2app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.k2app.R;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.pojo.PojoMenuDrawerOptions;
import com.app.k2app.pojo.PojoMenuDrawerUserPerfil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class AdapterMenuDrawer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<PojoMenuDrawerOptions> data = new ArrayList<PojoMenuDrawerOptions>();//Collections.emptyList();
    PojoMenuDrawerUserPerfil pojOusrMenuDrawerUserPerfil;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater inflater;
    private Context context;

    //Universal Image Loader
    private ImageLoader mImageLoader;

    public AdapterMenuDrawer(Context context, ArrayList<PojoMenuDrawerOptions> data, PojoMenuDrawerUserPerfil pojOusrMenuDrawerUserPerfil) {
        this.context = context;
        this.data = data;
        this.pojOusrMenuDrawerUserPerfil = pojOusrMenuDrawerUserPerfil;

        inflater = LayoutInflater.from(context);
        this.mImageLoader = MyApplication.getInstance().getImageLoader();
    }

    /*
    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }
    */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.adapter_drawer_header, parent, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        } else {
            View view = inflater.inflate(R.layout.adapter_drawer_item, parent, false);
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;

            headerHolder.user.setText(pojOusrMenuDrawerUserPerfil.getNmExibicao());
            Log.i(Config.TAG, "ADAPTER DRAWER name: " + pojOusrMenuDrawerUserPerfil.getNmExibicao());

            String URIPostImage = MyApplication.getWsUrlPhotoPerfil() + "/" + pojOusrMenuDrawerUserPerfil.getUserFoto();
            Log.i(Config.TAG, "ADAPTER DRAWER imageUser: " + URIPostImage);
            mImageLoader.displayImage(URIPostImage, ((HeaderHolder) holder).imageProfile);

            headerHolder.email.setText(pojOusrMenuDrawerUserPerfil.getEmail());
        } else {
            ItemHolder itemHolder = (ItemHolder) holder;
            PojoMenuDrawerOptions current = data.get(position - 1);
            itemHolder.title.setText(current.text);
            itemHolder.icon.setImageResource(current.icon);
        }

    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        ImageView imageProfile;
        TextView user;
        //TextView msgStatus;
        TextView email;

        public HeaderHolder(View itemView) {
            super(itemView);
            imageProfile = (ImageView) itemView.findViewById(R.id.iv_dh_imageprofile);
            user = (TextView) itemView.findViewById(R.id.tv_dh_user);
            //msgStatus = (TextView) itemView.findViewById(R.id.tv_dh_msguser);
            email = (TextView) itemView.findViewById(R.id.tv_dh_email);
        }
    }
}
