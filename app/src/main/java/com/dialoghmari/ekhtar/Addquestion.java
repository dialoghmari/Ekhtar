package com.dialoghmari.ekhtar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Addquestion extends AppCompatActivity {

    private static final String TAG_SUCCESS = "success";

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_create_question = "http://ekhtar.000webhostapp.com/create_question.php";

    private static Spinner categoriesp;
    private static EditText questionET, ch1ET, ch2ET, ch3ET, ch4ET, ch5ET;
    private Button addBtn;
    public static String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);

        uid = getIntent().getStringExtra("uid");

        questionET = (EditText) findViewById(R.id.questionET);
        ch1ET = (EditText) findViewById(R.id.ch1ET);
        ch2ET = (EditText) findViewById(R.id.ch2ET);
        ch3ET = (EditText) findViewById(R.id.ch3ET);
        ch4ET = (EditText) findViewById(R.id.ch4ET);
        ch5ET = (EditText) findViewById(R.id.ch5ET);

        addBtn = (Button) findViewById(R.id.addBtn);

        categoriesp = (Spinner) findViewById(R.id.categoriesp);


        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    new CreateNewQuestion().execute();
                }
            }
        });

    }
    class CreateNewQuestion extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Addquestion.this);
            pDialog.setMessage("Creation new question ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {


            String questionstr = questionET.getText().toString();
            String ch1str = ch1ET.getText().toString();
            String ch2str = ch2ET.getText().toString();
            String ch3str = ch3ET.getText().toString();
            String ch4str = ch4ET.getText().toString();
            String ch5str = ch5ET.getText().toString();
            String categoriestr = categoriesp.getSelectedItem().toString();

            // Building Parameters
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", uid));
            params.add(new BasicNameValuePair("content", questionstr));
            params.add(new BasicNameValuePair("ch1", ch1str));
            params.add(new BasicNameValuePair("ch2", ch2str));
            params.add(new BasicNameValuePair("ch3", ch3str));
            params.add(new BasicNameValuePair("ch4", ch4str));
            params.add(new BasicNameValuePair("ch5", ch5str));
            params.add(new BasicNameValuePair("categorie", categoriestr));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_question,"POST", params);

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
}
