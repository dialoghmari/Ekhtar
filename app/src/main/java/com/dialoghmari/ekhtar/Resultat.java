package com.dialoghmari.ekhtar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Resultat extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RESULTAT = "resultat";

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_question_results = "http://ekhtar.000webhostapp.com/get_question_result.php";

    private static String qid;
    private static String uid;
    private static String categorie;


    private static String content,ch1,ch2,ch3,ch4,ch5;
    TextView contentTVr, ch1TV,ch2TV,ch3TV,ch4TV,ch5TV;
    static TextView ch1p, ch2p, ch3p, ch4p, ch5p, nombrevoteTV, categorieTV;
    static Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        uid = getIntent().getStringExtra("uid");
        categorie = getIntent().getStringExtra("categorie");
        content = getIntent().getStringExtra("content");
        qid = getIntent().getStringExtra("qid");
        ch1 = getIntent().getStringExtra("ch1");
        ch2 = getIntent().getStringExtra("ch2");
        ch3 = getIntent().getStringExtra("ch3");
        ch4 = getIntent().getStringExtra("ch4");
        ch5 = getIntent().getStringExtra("ch5");

        contentTVr = (TextView) findViewById(R.id.contentTVr);
        ch1TV = (TextView) findViewById(R.id.ch1TV);
        ch2TV = (TextView) findViewById(R.id.ch2TV);
        ch3TV = (TextView) findViewById(R.id.ch3TV);
        ch4TV = (TextView) findViewById(R.id.ch4TV);
        ch5TV = (TextView) findViewById(R.id.ch5TV);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        categorieTV = (TextView) findViewById(R.id.categorieTV);


        ch1p = (TextView) findViewById(R.id.ch1p);
        ch2p = (TextView) findViewById(R.id.ch2p);
        ch3p = (TextView) findViewById(R.id.ch3p);
        ch4p = (TextView) findViewById(R.id.ch4p);
        ch5p = (TextView) findViewById(R.id.ch5p);

        nombrevoteTV = (TextView) findViewById(R.id.nombrevoteTV);

        categorieTV.setText(categorie);
        contentTVr.setText(content);
        ch1TV.setText(ch1);
        ch2TV.setText(ch2);
        ch3TV.setText(ch3);
        ch4TV.setText(ch4);
        ch5TV.setText(ch5);


        if(ch3.equals("")) {ch3TV.setVisibility(View.GONE); ch3p.setVisibility(View.GONE);}
        if(ch4.equals("")) {ch4TV.setVisibility(View.GONE); ch4p.setVisibility(View.GONE);}
        if(ch5.equals("")) {ch5TV.setVisibility(View.GONE); ch5p.setVisibility(View.GONE);}

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here


            new Resultat.GetQuestionResult().execute();
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Question.class);
                intent.putExtra("uid",uid);
                intent.putExtra("categorie",categorie);
                startActivity(intent);
                finish();
            }
        });
    }
    /* Getting results class */
    class GetQuestionResult extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Resultat.this);
            pDialog.setMessage("Loading question results. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        //List<NameValuePair> params = new ArrayList<NameValuePair>();
                        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("qid", qid));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(url_question_results, "GET", params);


                        // check your log for json response
                        Log.d("Question results", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received question results
                            JSONArray resultatObj = json.getJSONArray(TAG_RESULTAT);

                            // get first resultat object from JSON Array
                            JSONObject resultat = resultatObj.getJSONObject(0);

                            // display resultat data
                            ch1p.setText(resultat.getString("ch1p").toString()+"%");
                            ch2p.setText(resultat.getString("ch2p").toString()+"%");
                            ch3p.setText(resultat.getString("ch3p").toString()+"%");
                            ch4p.setText(resultat.getString("ch4p").toString()+"%");
                            ch5p.setText(resultat.getString("ch5p").toString()+"%");
                            nombrevoteTV.setText(resultat.getString("nombrevote").toString());


                        }else{
                            // question with qid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
}
