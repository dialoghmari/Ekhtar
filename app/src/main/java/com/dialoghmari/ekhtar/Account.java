package com.dialoghmari.ekhtar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Account extends AppCompatActivity {

    public static EditText nameET, emailET, birthdayET, nationalityET;
    public static String uid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_update_profile = "http://ekhtar.000webhostapp.com/update_profile.php";
    private static final String url_profile_detials = "http://ekhtar.000webhostapp.com/get_profile_details.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        uid = getIntent().getStringExtra("uid");

        nameET = (EditText) findViewById(R.id.nameET);
        emailET = (EditText) findViewById(R.id.emailET);
        birthdayET = (EditText) findViewById(R.id.birthdayET);
        nationalityET = (EditText) findViewById(R.id.nationalityET);

        // Getting complete product details in background thread
        new GetProfileDetails().execute();

        // save button click event
        Button btnEditProfile;
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveProfileEdit().execute();
            }
        });

    }
    class SaveProfileEdit extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Account.this);
            pDialog.setMessage("Saving profile settings ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            String nameStr = nameET.getText().toString();
            String emailStr = emailET.getText().toString();
            String birthdayStr = birthdayET.getText().toString();
            String nationalityStr = nationalityET.getText().toString();


            // Building Parameters
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", uid));
            params.add(new BasicNameValuePair("name", nameStr));
            params.add(new BasicNameValuePair("email", emailStr));
            params.add(new BasicNameValuePair("birthday", birthdayStr));
            params.add(new BasicNameValuePair("nationality", nationalityStr));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_profile,"POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
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

    class GetProfileDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Account.this);
            pDialog.setMessage("Loading user details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting user details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("uid", uid));

                        // getting user details by making HTTP request
                        // Note that user details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(url_profile_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single Profile Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received user details
                            JSONArray userObj = json.getJSONArray("user"); // JSON Array

                            // get first user object from JSON Array
                            JSONObject user = userObj.getJSONObject(0);

                            // user with this pid found
                            // display product data in EditText
                            nameET.setText(""+user.getString("name"));
                            emailET.setText(""+user.getString("email"));
                            birthdayET.setText(""+user.getString("birthday"));
                            nationalityET.setText(""+user.getString("nationality"));

                        }else{
                            // product with pid not found
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
