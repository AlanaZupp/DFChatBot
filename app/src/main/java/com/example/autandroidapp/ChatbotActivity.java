package com.example.autandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 */
public class ChatbotActivity extends AppCompatActivity
{
    TextView chatbotReply, userMessage;
    EditText editbox;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homePageIntent = new Intent(this, MainActivity.class);
                this.startActivity(homePageIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        Intent intent = getIntent();
        chatbotReply=findViewById(R.id.textMsgField);
        userMessage=findViewById(R.id.userTextView);
        editbox=findViewById(R.id.editText);
    }

    /**
     *
     * @param query - the user sent message
     * @return the response from dialog api
     * @throws UnsupportedEncodingException
     */
    public String getRes(String query) throws UnsupportedEncodingException
    {
        String text = "";
        BufferedReader reader = null;

        try{
            //Connecting to DialogFlow v1 through
            URL url = new URL("https://api.dialogflow.com/v1/query?v=20150910");
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization","Bearer 5937957ad218472c86b898fdb1662e73"); //client access token
            connection.setRequestProperty("Content-Type","application/json");

            //Creating a JSON object that holds user message
            JSONObject jsonParameter = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query); //push into array
            jsonParameter.put("query",queryArray);
            jsonParameter.put("lang","en");
            jsonParameter.put("sessionId","1234567890"); //session id is the same for everyone

            //writing the json object to dialog
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(jsonParameter.toString());
            wr.flush();

            //reading the response from dialog
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line= reader.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            text = sb.toString(); //all data returned by the dialogflow

            //un-indenting JSON file structure for dialogflow response
            JSONObject object = new JSONObject(text);
            JSONObject secObject = object.getJSONObject("result");
            JSONObject fulfill = null;
            String speech = null;
            if (secObject.has("fulfillment"))
            {
                fulfill= secObject.getJSONObject("fulfillment");
                if (fulfill.has("speech"))
                {
                    speech = fulfill.optString("speech");
                }
            }
            return speech;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... voids) {
            String query = null;
            try{
                query = getRes(voids[0]); //sends user message
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return query;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            chatbotReply.setText(s); //sets dialogflow reply
        }
    }

    /**
     *
     */
    public void sendMessage(View view) {
        userMessage.setText(editbox.getText().toString());
        editbox.setText(""); //clear editbox on send
        RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute(userMessage.getText().toString());
    }
}