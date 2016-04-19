package com.app.k2app.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.app.k2app.JSON.Base64Utils;
import com.app.k2app.R;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.network.VolleySingleton;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ActivityPostAdd extends ActionBarActivity {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;

    private Toolbar mToolbar;
    private EditText etPostTxt;
    private ImageView ivPhotoImg;
    private Button btPhoto;
    private Button btGallery;

    Bitmap selectedImage = null;

    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        mToolbar = (Toolbar) findViewById(R.id.tb_postAdd);
        mToolbar.setTitle("K2PIO");
        mToolbar.setSubtitle("Post add");
        mToolbar.setLogo(R.mipmap.ic_action_k2pio);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Quandro para mostrar a foto
        ivPhotoImg = (ImageView) findViewById(R.id.ivPhotoImg);

        // Button Gallery Photo
        btGallery = (Button) findViewById(R.id.btGallery);
        btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageGalleryCapture();
            }
        });

        // Button Capture Photo
        btPhoto = (Button) findViewById(R.id.btPhoto);
        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = getBaseContext().getPackageManager();
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) == true) {
                    imageCameraCapture();
                } else {
                    Toast.makeText(getBaseContext(), "This device does not have a camera.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        etPostTxt = (EditText) findViewById(R.id.etPostTxt);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_post_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sendpost) {
            this.postSend();
        }

        return super.onOptionsItemSelected(item);
    }

    public void imageGalleryCapture() {

        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File tempFile = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            Uri tempUri = Uri.fromFile(tempFile);
            Intent pickCropImageIntent = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickCropImageIntent.setType("image/*");
            pickCropImageIntent.putExtra("crop", "true");
            pickCropImageIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    tempUri);
            pickCropImageIntent.putExtra("aspectX", 0);
            pickCropImageIntent.putExtra("aspectY", 0);
            pickCropImageIntent.putExtra("outputX", 640);
            pickCropImageIntent.putExtra("outputY", 640);
            pickCropImageIntent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            pickCropImageIntent.putExtra("return-data", true);
            startActivityForResult(pickCropImageIntent,
                    PICK_FROM_GALLERY);
        } else {

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    private void imageCameraCapture() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getBaseContext(), "There was a problem saving the photo.", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile).toString());
                Log.i(Config.TAG, "Uri.fromFile(photoFile).toString(): " + Uri.fromFile(photoFile).toString());
                // ******** code for crop image
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 640);
                intent.putExtra("outputY", 640);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("scale", true);
                intent.putExtra("return-data", false);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(Config.TAG, "onActivityResult DATA: " + data);

        if (data != null) {
            if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                selectedImage = (Bitmap) extras.get("data"); //only a thumbs
                ivPhotoImg.setImageBitmap(selectedImage);
            }

            if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
                selectedImage = BitmapFactory.decodeFile(Environment
                        .getExternalStorageDirectory() + "/temp.jpg");
                ivPhotoImg.setImageBitmap(selectedImage);
            }
        }
    }



    private void postSend() {

        try {

            ActivityMain am = new ActivityMain();
            userId=am.getUserId();

            String postTxt = String.valueOf(etPostTxt.getText());
            Log.i(Config.TAG, "postTxt: " + postTxt);

            if (!postTxt.equals("") || selectedImage != null){

                String Filtro = "/posts";

                String URI = MyApplication.getWsUrlApp() + Filtro;
                Log.i(Config.TAG, "URL: " + URI);

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(userId));

                if (!postTxt.equals("")){
                    params.put("postText", postTxt);
                }

                // *****  Transforma imagem para string ********
                if (selectedImage != null){
                    String imgBase64 = Base64Utils.bmpToStrBase64(selectedImage);
                    params.put("postImgUrl", imgBase64);
                }

                // pass second argument as "null" for GET requests
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Integer status = response.getInt("status");
                                    Log.i(Config.TAG, "Resposta Status JSON: " + status);
                                    if (status == 0) {
                                        Toast.makeText(getBaseContext(), "Post send with sucess", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getBaseContext(), "Timeout", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getBaseContext(), "No Connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getBaseContext(), "Auth Failure", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getBaseContext(), "Network Error", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getBaseContext(), "Parse Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // add the request object to the queue to be executed
                VolleySingleton.getInstance().addToRequestQueue(req);
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! Put a text or image.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
