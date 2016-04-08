package com.app.k2app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
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
import com.app.k2app.JSON.ParserLogin;
import com.app.k2app.R;
import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.network.VolleySingleton;
import com.app.k2app.pojo.PojoLogin;
import com.app.k2app.pojo.PojoPerfilUser;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.HashMap;

public class ActivityLogin extends ActionBarActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    PojoLogin pojoLogin;

    /**
     * GOOGLE CLIENT
     */
    public static final int RC_SIGN_IN = 69;
    public static GoogleApiClient mGoogleApiClient;

    // Existe uma resolução ConnectionResult em andamento?
    private boolean mIsResolving = false;

    // Devemos resolver automaticamente ConnectionResults quando possível?
    private boolean mShouldResolve = false;

    // Botao de login Google+ api
    private SignInButton signInButton;

    // A progress dialog to display when the user is connecting in
    // case there is a delay in any of the dialogs being ready.
    private ProgressDialog mConnectionProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(Config.TAG, "ActivityLogin onCreate");

        //Buttons Google
        signInButton = (SignInButton) findViewById(R.id.btn_google_sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        signInButton.setOnClickListener(this);

        // Build GoogleApiClient to request access to the basic user profile
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addApi(AppIndex.API).build();

        // Configure the ProgressDialog that will be shown if there is a
        // delay in presenting the user with the next sign in step.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");
    }


    @Override
    protected void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
        Log.i(Config.TAG, "ActivityLogin onStart: mGoogleApiClient.connect();");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Config.TAG, "ActivityLogin onStop: mGoogleApiClient.disconnect();");
        //mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Config.TAG, "ActivityLogin ON DESTROY");
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_google_sign_in) {
            onSignInClicked();
        }
    }

    private void onSignInClicked() {
        Log.i(Config.TAG, "ActivityLogin: onSignInClicked()");

        // Usuário clicou no botão sign-in, para iniciar o processo de login
        // e automaticamente tentar resolver quaisquer erros que ocorram.
        mShouldResolve = true;
        //tenta a conexao
        mGoogleApiClient.connect();
        // Show the dialog as we are now signing in.
        mConnectionProgressDialog.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(Config.TAG, "onConnectionFailed: " + connectionResult);

        //Não foi possível conectar para jogar Serviços do Google.
        // O usuário precisa selecionar uma conta, conceder permissões
        // ou resolver um erro, a fim de entrar.

        Log.i(Config.TAG, "mIsResolving: " + mIsResolving);
        Log.i(Config.TAG, "mShouldResolve: " + mShouldResolve);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                Log.i(Config.TAG, "onConnectionFailed: Tem solucao.");
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(Config.TAG, "Não pode resolver ConnectionResult: " + e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Não foi possível resolver o resultado da ligação, mostrar ao usuário um diálogo de erro.
                Log.e(Config.TAG, "Não foi possível resolver o resultado da ligação: " + connectionResult);
            }
        } else {
            Log.i(Config.TAG, "Esta resolvendo ou não deve resolver");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Config.TAG, "onActivityResult - requestCode: " + requestCode + "  resultCode: " + resultCode + "  data: " + data);

        if (requestCode == RC_SIGN_IN) {
            // Se a resolução de erro não foi bem sucedida, não devemos resolver ainda.
            if (resultCode != RESULT_OK) {
                Log.i(Config.TAG, "resultCode != RESULT_OK . resultCode = " + resultCode);
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(Config.TAG, "onConnectionSuspended()");

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // OnConnected indica que uma conta foi selecionado no dispositivo,
        // que a conta selecionada concedeu as permissões solicitadas para o nosso app
        // e que fomos capazes de estabelecer uma conexão de serviço para o Google Play serviços.
        // Estamos Conectados :)

        Log.d(Config.TAG, "ActivityLogin +++ Connected:" + bundle);
        mShouldResolve = false;

        //recebe informacoes do perfil google
        PojoPerfilUser pojoPUser = getGoogleDataProfile();
        //verifica se o usuario existe na database k2pio
        checkLocalUserDB(pojoPUser);
        // Hide the progress dialog if its showing.
        mConnectionProgressDialog.dismiss();
    }

    public PojoPerfilUser getGoogleDataProfile() {
        Log.i(Config.TAG, "ActivityLogin: +++ getGoogleDataProfile()");

        PojoPerfilUser pojoPerfilUser = new PojoPerfilUser();

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Log.i(Config.TAG, "ActivityLogin: +++ Plus.PeopleApi.getCurrentPerson(mGoogleApiClient)");
            Person p = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            //email
            if (Plus.AccountApi.getAccountName(mGoogleApiClient) != null) {
                pojoPerfilUser.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
            }
            //nascimento
            if (p.hasBirthday()) {
                pojoPerfilUser.setNascimento(p.getBirthday());
            } else {
                pojoPerfilUser.setNascimento("1950-01-01");
                Log.i(Config.TAG, "ActivityLogin: Plus.AccountApi.getBirthday NULL: " + p.getBirthday());
            }
            //sexo
            if (p.hasGender()) {
                Log.i(Config.TAG, "ActivityLogin: p.getGender: " + p.getGender());
                if (p.getGender() != 0 && p.getGender() != 1) {
                    pojoPerfilUser.setSexo(0); //0-man 1-girl 2-recusar dizer
                } else {
                    pojoPerfilUser.setSexo(p.getGender());
                }
            } else {
                pojoPerfilUser.setSexo(0);
            }

            //orientaçao sexual
            pojoPerfilUser.setOriSexual(0);
            //nome de exibicao
            if (p.getDisplayName() == null) {
                pojoPerfilUser.setNmExibicao("sem nome");
            } else {
                pojoPerfilUser.setNmExibicao(p.getDisplayName());
            }
            /*
            * OPTIONAL
            */
            pojoPerfilUser.setNmFoto(p.getImage().getUrl());
            pojoPerfilUser.setLang(p.getLanguage());
            pojoPerfilUser.setMsgStatus(p.getAboutMe());

            // NOT NULL
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() email - " + pojoPerfilUser.getEmail());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() birthday - " + pojoPerfilUser.getNascimento());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() sex - " + pojoPerfilUser.getSexo());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() oriSex - " + pojoPerfilUser.getOriSexual());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() showName - " + pojoPerfilUser.getNmExibicao());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() imagePerfil - " + pojoPerfilUser.getNmFoto());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() displayName - " + pojoPerfilUser.getNmExibicao());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() language -  " + pojoPerfilUser.getLang());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() Gender - " + pojoPerfilUser.getSexo());
            Log.i(Config.TAG, "ActivityLogin: getGoogleDataProfile() AboutMe - " + pojoPerfilUser.getMsgStatus());

        }
        return pojoPerfilUser;
    }

    public void checkLocalUserDB(final PojoPerfilUser pojoPerfilUser) {
        Log.i(Config.TAG, "ActivityLogin: +++ checkLocalUserDB()");
        Log.i(Config.TAG, "ActivityLogin: +++ checkLocalUserDB(): email " + pojoPerfilUser.getEmail());

        try {
            if (pojoPerfilUser.getEmail() != null) {
                Log.i(Config.TAG, "ActivityLogin: checkLocalUserDB(" + pojoPerfilUser.getEmail() + ")");

                String urlLogin = "/login";
                String URI = MyApplication.getWsUrlApp() + urlLogin + "/" + pojoPerfilUser.getEmail();
                Log.i(Config.TAG, "URL: " + URI);

                // pass second argument as "null" for GET requests
                JsonObjectRequest req = new JsonObjectRequest(URI, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pojoLogin = ParserLogin.ParserJSONLogin(response);
                                if (pojoLogin.getEmailOK() == 0) { //Não existe
                                    Log.i(Config.TAG, "ActivityLogin: + pojoLogin.getEmailOK = 0 : NAO EXISTE");
                                    insertUserDB(pojoPerfilUser);
                                } else if (pojoLogin.getEmailOK() == 1) { //Existe
                                    Log.i(Config.TAG, "ActivityLogin: + pojoLogin.getEmailOK = 1 : EXISTE");
                                    doLogin(pojoLogin.getId());
                                } else if (pojoLogin.getEmailOK() == 2) { //Outro status
                                    Log.i(Config.TAG, "ActivityLogin: + pojoLogin.getEmailOK = 2 : OUTRO STATUS");
                                    Toast.makeText(MyApplication.getAppContext(), "Your user is blocked ;(", Toast.LENGTH_LONG).show();
                                }
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
                });// add the request object to the queue to be executed
                VolleySingleton.getInstance().addToRequestQueue(req);
            } else {
                Toast.makeText(getBaseContext(), "Not email", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertUserDB(PojoPerfilUser pojoPerfilUser) {
        Log.i(Config.TAG, "ActivityLogin: + insertUserDB");

        try {
            if (pojoPerfilUser != null) {

                String addUsers = "/users";
                String URI = MyApplication.getWsUrlApp() + addUsers;
                Log.i(Config.TAG, "ActivityLogin - URL: " + URI);

                //utilizar o mesmo value do webservice
                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("email", String.valueOf(pojoPerfilUser.getEmail()));
                params.put("birthday", String.valueOf(pojoPerfilUser.getNascimento()));
                params.put("sex", String.valueOf(pojoPerfilUser.getSexo()));
                params.put("oriSex", String.valueOf(pojoPerfilUser.getOriSexual()));
                params.put("showName", String.valueOf(pojoPerfilUser.getNmExibicao()));
                String str = String.valueOf(pojoPerfilUser.getNmFoto());
                int strLenght = str.length();
                String urlPerfil = str.substring(0, strLenght - 2);
                params.put("urlImgPerfilGoogle", urlPerfil + "640");
                params.put("lat", String.valueOf(-12.8795775));
                params.put("lon", String.valueOf(-38.2327052));

                Log.i(Config.TAG, "ActivityLogin: insertUserDB params : " + params);

                final PojoPerfilUser user1 = pojoPerfilUser;

                // pass second argument as "null" for GET requests
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i(Config.TAG, "Resposta Status JSON Response: " + response);
                                    Integer status = response.getInt("status");
                                    //cadastrado com sucesso
                                    if (status == 0) {
                                        checkLocalUserDB(user1);
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
                });// add the request object to the queue to be executed
                VolleySingleton.getInstance().addToRequestQueue(req);
            } else {
                Toast.makeText(getBaseContext(), "Not email", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doLogin(Integer id) {
        Log.i(Config.TAG, "ActivityLogin: + doLogin");

        Bundle params = new Bundle();
        params.putInt("idUser", id);
        Intent intent = new Intent(getBaseContext(), ActivityMain.class);
        intent.putExtras(params);
        startActivity(intent);
        Log.i(Config.TAG, "startActivity(intent): " + params);
    }

    /**
     * Sign-out from google
     */
    public static void signOutFromGplus() {
        Log.d(Config.TAG, "ActivityLogin signOutFromGplus()");

        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Revoking access from google
     */
    public static void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.i(Config.TAG, "ActivityLogin: User access revoked!");
                            mGoogleApiClient.disconnect();
                            //mGoogleApiClient.connect();
                        }
                    });
        }
    }


}
