package com.example.autandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class for the chatbot and handles the connections between other classes
 * as well as changes made to the users view.
 */
public class ChatbotActivity extends AppCompatActivity
{
    TextView chatbotReply, userMessage;
    EditText editbox;
    private RecyclerView msgRecyclerView;
    List<ChatMsgList> msgList;
    ChatAdapter adapter;
    MediaPlayer messageRec, messageSend;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homePageIntent = new Intent(this, MainActivity.class);
                this.startActivity(homePageIntent);
                messageRec.release(); //release the media players to save space
                messageSend.release();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *The method that is called when the use access the chatbot section of the app.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        Intent intent = getIntent();

        if(messageRec == null){ //If the media player for a message recieved is already created don't create another.
        messageRec = MediaPlayer.create(this,R.raw.get_outta_here);
        }
        if(messageSend==null){ //IF the media player for a message sent is already created don't create another
            messageSend = MediaPlayer.create(this, R.raw.inquisitiveness);
        }

        editbox=findViewById(R.id.editText); //initializes the users message box

        msgRecyclerView = (RecyclerView)findViewById(R.id.chatRV); //Sets the recycler view which allows the messages to be recycled
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        msgList = new ArrayList<ChatMsgList>(); //Creates the inital message list, assigning the first message as Welcome!, on the chat bots side.
        ChatMsgList chatMsg = new ChatMsgList(ChatMsgList.Msg_rece,"Welcome!");
        msgList.add(chatMsg);

        adapter = new ChatAdapter(msgList); //sets the adapter to hold the message list
        msgRecyclerView.setAdapter(adapter); //connects the adapter to the recycler
    }

    /**
     * This is the method to get the response from dialogflow after the users query has been passed.
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
            connection.setRequestProperty("Authorization","Bearer acd43e00b5dc415685c033294a283b7a");
            connection.setRequestProperty("Content-Type","application/json");

            //Creating a JSON object that holds user message
            JSONObject jsonParameter = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query); //push into array
            jsonParameter.put("query",queryArray);
            jsonParameter.put("lang","en");
            jsonParameter.put("sessionId","1234567890"); //session id is the same for everyone doesnt need to be hidden

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
            text = sb.toString(); //all data returned by dialogflow

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

    /**
     * This method is used to call getRes to get dialog flows response
     * and then calls receiveMessage that will add the chatbots response to the list of messages.
     */
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
            try {
                recieveMessage(s); //sends dialog flow response to the chat recycler
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  This method takes the text supplied by the user and generates a message in the chat
     *  it also plays a sound unique to the user sending a message as well as clears the users
     *  message box
     * @param view
     * @throws InterruptedException
     */
    public void sendMessage(View view) {
          String userMsg = editbox.getText().toString();
          if(!TextUtils.isEmpty(userMsg))
          {
                ChatMsgList chatMsg = new ChatMsgList(ChatMsgList.Msg_sent, userMsg); //create new chat message add it to the list
                msgList.add(chatMsg);
                int msgPost = msgList.size()-1;
                editbox.setText(""); //clear user edit box
                messageSend.start(); //play the message sound
                adapter.notifyItemInserted(msgPost); //update chat recycler
                msgRecyclerView.scrollToPosition(msgPost);
                //Thread.sleep(500); //sleep for .5sec to allow for sound
                RetrieveFeedTask task = new RetrieveFeedTask(); //call for dialog flows response
                task.execute(userMsg);
          }
    }

    /**
     * This method takes the response given by dialog flow and generates a message in the chat,
     * it also plays a sound unique to the chat bot responding to the user.
     * @param response
     * @throws InterruptedException
     */
    public void recieveMessage(String response) throws InterruptedException {
        if(!TextUtils.isEmpty(response)) { //if the chatbots response is not empty
            ChatMsgList chatMsg = new ChatMsgList(ChatMsgList.Msg_rece, response); //create a new message and add that to the chat
            msgList.add(chatMsg);
            int msgPost = msgList.size() - 1;
            messageRec.start(); //play the sound unique to the chat bot responding
            Thread.sleep(500);// sleep to allow time for the sound to play
            adapter.notifyItemInserted(msgPost); //insert the new message into the recycler
            msgRecyclerView.scrollToPosition(msgPost);
        }
    }
}