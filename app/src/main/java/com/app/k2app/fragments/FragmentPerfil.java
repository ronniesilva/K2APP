package com.app.k2app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.k2app.JSON.ParserPerfil;
import com.app.k2app.R;
import com.app.k2app.activities.ActivityPerfil;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.network.VolleySingleton;
import com.app.k2app.pojo.PojoPerfilUserPhotos;
import com.app.k2app.pojo.PojoPerfilUser;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentPerfil extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ImageView ivPerfilImage;
    private TextView tvPerfilNome;
    private TextView tvPerfilIdade;
    private TextView tvPerfilMsgStatus;
    private TextView tvPerfilEmail;
    private TextView tvPerfilTel;
    private TextView tvPerfilNascimento;
    private TextView tvPerfilSexo;
    private TextView tvPerfilOriSex;

    Integer PERFIL_ID;

    private SliderLayout slImages;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        ActivityPerfil activity = (ActivityPerfil) getActivity();
        PERFIL_ID = activity.getUserID();

        // SLIDERLAYOUT
        slImages = (SliderLayout) v.findViewById(R.id.sliderImages);
        //slImages.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        //slImages.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        //slImages.setCustomAnimation(new DescriptionAnimation());
        //slImages.setDuration(4000);
        //slImages.setSliderTransformDuration(8000, null);
        //slImages.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        //PagerIndicator p = (PagerIndicator) v.findViewById(R.id.custom_indicator);
        //slImages.setCustomIndicator((PagerIndicator) v.findViewById(R.id.sliderCustomIndicator));

        tvPerfilNome = (TextView) v.findViewById(R.id.tvPerfilNome);
        tvPerfilIdade = (TextView) v.findViewById(R.id.tvPerfilIdade);
        tvPerfilMsgStatus = (TextView) v.findViewById(R.id.tvPerfilMsgStatus);
        tvPerfilEmail = (TextView) v.findViewById(R.id.tvPerfilEmail);
        tvPerfilTel = (TextView) v.findViewById(R.id.tvPerfilTel);
        tvPerfilNascimento = (TextView) v.findViewById(R.id.tvPerfilNascimento);
        tvPerfilSexo = (TextView) v.findViewById(R.id.tvPerfilSexo);
        tvPerfilOriSex = (TextView) v.findViewById(R.id.tvPerfilOriSex);

        return v;
    }


    public void showPerfil(Integer id) {

        try {

            String URI = MyApplication.getWsUrlApp() + "/users/" + id;

            Log.i(Config.TAG, "FRAGMENT PERFIL URL: " + URI);

            // pass second argument as "null" for GET requests
            JsonObjectRequest req = new JsonObjectRequest(URI, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //set Parser
                            PojoPerfilUser dataPerfil = ParserPerfil.ParserJSONPerfil(response);
                            Log.i(Config.TAG, "FRAGMENT PERFIL dataPerfil nmexibicao: " + dataPerfil.getNmExibicao());

                            //Get Imagem URI
                            String DirPhotoPerfil = MyApplication.getWsUrlPhotoPerfil() + "/";

                            ArrayList<PojoPerfilUserPhotos> arrayFotos = dataPerfil.getArrayFotos();
                            Log.i(Config.TAG, "FRAGMENT PERFIL dataPerfil dataPerfil.getArrayFotos(): " + dataPerfil.getArrayFotos());
                            for (int i = 0; i <  arrayFotos.size(); i++) {
                                // SLIDERLAYOUT
                                TextSliderView slider = new TextSliderView(getActivity().getBaseContext());

                                // initialize a SliderLayout
                                slider.image(DirPhotoPerfil + arrayFotos.get(i).getNomeFoto());
                                slider.setScaleType(BaseSliderView.ScaleType.CenterCrop);
                                slider.description(arrayFotos.get(i).getLegenda());
                                //.setOnSliderClickListener(MainActivity.this)
                                //.setOnImageLoadListener(MainActivity.this)
                                slImages.addSlider(slider);
                            }

                            tvPerfilNome.setText(dataPerfil.getNmExibicao());
                            tvPerfilMsgStatus.setText(dataPerfil.getMsgStatus());
                            tvPerfilEmail.setText(dataPerfil.getEmail());
                            tvPerfilTel.setText(dataPerfil.getTel());
                            tvPerfilNascimento.setText(dataPerfil.getNascimento());
                            //tvPerfilSexo.setText(dataPerfil.getSexo().toString());
                            //tvPerfilOriSex.setText(dataPerfil.getOriSexual().toString());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    if (error instanceof TimeoutError) {
                        Toast.makeText(MyApplication.getAppContext(), "Timeout", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(MyApplication.getAppContext(), "No Connection", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(MyApplication.getAppContext(), "Auth Failure", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(MyApplication.getAppContext(), "Server Error", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(MyApplication.getAppContext(), "Network Error", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(MyApplication.getAppContext(), "Parse Error", Toast.LENGTH_LONG).show();
                    }
                }
            });

            // add the request object to the queue to be executed
            VolleySingleton.getInstance().addToRequestQueue(req);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        showPerfil(PERFIL_ID);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, "FRAGMENT PERFIL onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showPerfil(PERFIL_ID);
        Log.i(Config.TAG, "FRAGMENT PERFIL onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(Config.TAG, "FRAGMENT PERFIL onPause");
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        slImages.stopAutoCycle();
        Log.i(Config.TAG, "FRAGMENT PERFIL onStop");
        super.onStop();
    }

}