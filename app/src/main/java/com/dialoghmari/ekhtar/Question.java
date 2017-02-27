package com.dialoghmari.ekhtar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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

import static android.os.Build.VERSION.SDK_INT;

public class Question extends Activity {

    TextView contentTV;
    Button ch1Button;
    Button ch2Button;
    Button ch3Button;
    Button ch4Button;
    Button ch5Button;

    private static String content,ch1,ch2,ch3,ch4,ch5;

    public static String categorie;
    public static String qid;
    public static String uid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_question_detials = "http://ekhtar.000webhostapp.com/get_question_details.php";
    private static final String url_add_vote = "http://ekhtar.000webhostapp.com/vote.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_QUESTION = "question";
    private static final String TAG_QID = "qid";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_CH1 = "ch1";
    private static final String TAG_CH2 = "ch2";
    private static final String TAG_CH3 = "ch3";
    private static final String TAG_CH4 = "ch4";
    private static final String TAG_CH5 = "ch5";


    private static String CHOIX = "ch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        uid = getIntent().getStringExtra("uid");
        categorie = getIntent().getStringExtra("categorie");
        contentTV = (TextView) findViewById(R.id.contentTV);
        ch1Button = (Button) findViewById(R.id.ch1Button);
        ch2Button = (Button) findViewById(R.id.ch2Button);
        ch3Button = (Button) findViewById(R.id.ch3Button);
        ch4Button = (Button) findViewById(R.id.ch4Button);
        ch5Button = (Button) findViewById(R.id.ch5Button);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here


            new GetQuestionDetails().execute();
        }

    }
    /* Getting question information class */
    class GetQuestionDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Question.this);
            pDialog.setMessage("Loading question details. Please wait...");
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
                        params.add(new BasicNameValuePair("categorie", categorie));
                        params.add(new BasicNameValuePair("uid", uid));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(url_question_detials, "GET", params);


                        // check your log for json response
                        Log.d("Single Question Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received question details
                            JSONArray questionObj = json.getJSONArray(TAG_QUESTION); // JSON Array

                            // get first question object from JSON Array
                            JSONObject question = questionObj.getJSONObject(0);

                            // display question data
                            qid = question.getString(TAG_QID);
                            contentTV.setText(question.getString(TAG_CONTENT));
                            ch1Button.setText(""+question.getString(TAG_CH1));
                            ch2Button.setText(""+question.getString(TAG_CH2));

                            content = question.getString(TAG_CONTENT);
                            ch1 = question.getString(TAG_CH1);
                            ch2 = question.getString(TAG_CH2);
                            ch3 = question.getString(TAG_CH3);
                            ch4 = question.getString(TAG_CH4);
                            ch5 = question.getString(TAG_CH5);


                            if(question.getString(TAG_CH3).equals("")) ch3Button.setVisibility(View.GONE); else ch3Button.setText(""+question.getString(TAG_CH3));
                            if(question.getString(TAG_CH4).equals("")) ch4Button.setVisibility(View.GONE); else ch4Button.setText(""+question.getString(TAG_CH4));
                            if(question.getString(TAG_CH5).equals("")) ch5Button.setVisibility(View.GONE); else ch5Button.setText(""+question.getString(TAG_CH5));

                            ch1Button.setOnClickListener(myOnlyhandler);
                            ch2Button.setOnClickListener(myOnlyhandler);
                            ch3Button.setOnClickListener(myOnlyhandler);
                            ch4Button.setOnClickListener(myOnlyhandler);
                            ch5Button.setOnClickListener(myOnlyhandler);



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


    /* Voting for a question class*/
    class VoteForQuestion extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Question.this);
            pDialog.setMessage("Voting for...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Vote
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("qid", qid));
            params.add(new BasicNameValuePair("uid", uid));
            params.add(new BasicNameValuePair("choix", CHOIX));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_vote,"POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    //finish();
                    Intent intent = new Intent(getApplicationContext(),Resultat.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("qid",qid);
                    intent.putExtra("categorie",categorie);

                    // sending question and choices
                    intent.putExtra("content",content);
                    intent.putExtra("ch1",ch1);
                    intent.putExtra("ch2",ch2);
                    intent.putExtra("ch3",ch3);
                    intent.putExtra("ch4",ch4);
                    intent.putExtra("ch5",ch5);

                    startActivity(intent);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

     View.OnClickListener myOnlyhandler = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.ch1Button:
                    CHOIX = "ch1";
                    break;
                case R.id.ch2Button:
                    CHOIX = "ch2";
                    break;
                case R.id.ch3Button:
                    CHOIX = "ch3";
                    break;
                case R.id.ch4Button:
                    CHOIX = "ch4";
                    break;
                case R.id.ch5Button:
                    CHOIX = "ch5";
                    break;
            }
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here


                new VoteForQuestion().execute();
            }
        }
    };

}
