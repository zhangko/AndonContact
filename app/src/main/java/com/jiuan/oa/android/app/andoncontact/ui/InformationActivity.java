package com.jiuan.oa.android.app.andoncontact.ui;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jiuan.oa.android.app.andoncontact.ContactApplication;
import com.jiuan.oa.android.app.andoncontact.R;
import com.jiuan.oa.android.app.andoncontact.adapter.MyAdapter;
import com.jiuan.oa.android.app.andoncontact.im.Header;
import com.jiuan.oa.android.app.andoncontact.im.ImMessage;
import com.jiuan.oa.android.app.andoncontact.im.Information;
import com.jiuan.oa.android.app.andoncontact.im.MessageContract;
import com.jiuan.oa.android.app.andoncontact.im.MobileIMHeader;
import com.jiuan.oa.android.app.andoncontact.im.MyJsonRequest;
import com.jiuan.oa.android.app.andoncontact.im.MyRequest;
import com.jiuan.oa.android.app.andoncontact.im.RandString;
import com.jiuan.oa.android.app.andoncontact.im.UserRequest;
import com.jiuan.oa.android.library.http.login.OALoginResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ZhangKong on 2015/7/24.
 */
public class InformationActivity extends ActionBarActivity implements View.OnClickListener {

    private  MyAdapter adapter;

    private String chat_information;

    private List<ImMessage> listString = new ArrayList<ImMessage>();

    private EditText editText;

    private String receiverID;

    private String receiverName;

    private String senderID;

    private String senderName;

    private Socket socket;

    private PrintWriter out;

    private Gson gson;

    private Header header = new Header();

    private String headerinfo;

    private Header heart = new Header();

    private String heartinfo;

    private String receiveinfo;

    private  final String URL = "http://192.168.12.67:10011/MobileServerPlat/SaveIM/";

    private final String STATEURL = "http://192.168.12.67:10011/MobileServerPlat/UpDateMessageState/";

    private final String REQUEST_ALL_URL = "http://192.168.12.67:10011/MobileServerPlat/GetUnReadMessageList/";

    private RequestQueue requestQueue;

    private String path;

    private Handler handler;

    private ImMessage receiverMessage;

    private MyRequest myRequest;

    private MyJsonRequest myJsonRequest;



    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.information_activity);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        receiverID = getIntent().getStringExtra("staffID");
        receiverName = getIntent().getStringExtra("name");
        Log.d("TAG","  " + receiverName + "   "  + receiverID);
        OALoginResponse oaLoginResponse = ContactApplication.loadInfo(this);
        senderID = oaLoginResponse.getUserID();
        senderName = oaLoginResponse.getUserName();
        requestQueue = Volley.newRequestQueue(this);
        Log.d("TAG", " " + senderName + "    " + senderID);
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        handler  = new Handler(){
            @Override
            public void handleMessage(Message message){
                adapter.addItem(receiverMessage);
                super.handleMessage(message);
            }
        };

}

    @Override
    public void  onResume(){
        super.onResume();

        getSocketContact();
        getUnreandMessage();
    }
    @Override
    public void onStop(){
        super.onStop();
        if(socket != null){
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }


    }
    private void initView(){

         editText = (EditText)findViewById(R.id.informationactivity_edittext);
        Log.d("editText", " " + editText.getHeight());
        Button button = (Button)findViewById(R.id.informationactivity_button);
        button.setOnClickListener(this);
        Log.d("button","  " + button.getHeight());
        adapter = new MyAdapter(this,R.layout.adapter_layout,listString);
        ListView  listView = (ListView)findViewById(R.id.information_list);
        listView.setAdapter(adapter);
    }

    private void getSocketContact(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUserID(senderID);
        userRequest.setUserDisplayName(senderName);
        heart.setInformationType(8);
        heart.setFragment("");
        heartinfo = gson.toJson(heart);
        String fragment = gson.toJson(userRequest);
        header.setFragment(fragment);
        headerinfo = gson.toJson(header);
        Log.d("heartinfo"," " + heartinfo);
        Log.d("headerinfo"," " + headerinfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("TAG","connecting.......");
                    socket = new Socket("192.168.1.159",4531);

                    Log.d("TAG","socket connected.......");
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);
                    out.println(headerinfo);
                    Log.d("TAG", " " + out.toString());
                    Log.d("登陆TAG", "发送数据！");
                    out.flush();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true)
                            {
                                try{
                                    Thread.sleep(30000);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }

                                Log.d("心跳TAG"," "  + heartinfo);
                                out.println(heartinfo);
                                out.flush();
                            }
                        }
                    }).start();

                    Log.d("TAG","准备接受数据！");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    while(true){
                        receiveinfo = in.readLine();
                        Log.d("BufferedReader"," " + receiveinfo);
                        Log.d("TAG","接收了数据");
                        Header receive_header = gson.fromJson(receiveinfo,Header.class);
                        Log.d("Fragment"," " + receive_header.getFragment());
                        Log.d("InformationType","  " +  receive_header.getInformationType());
                        switch (receive_header.getInformationType()){
                            case 1:
                                Log.d("类型一的数据是："," "  + receive_header.getFragment());
                                break;
                            case 8:
                                Log.d("类型八的数据是：","  " + receive_header.getFragment());
                                break;
                            case 6:
                                Log.d("类型六的数据是：","  " +  receive_header.getFragment());
                                receiverMessage = new ImMessage();
                                receiverMessage.setType(2);
                                String text = receive_header.getFragment();
                                MessageContract messageContract = gson.fromJson(text, MessageContract.class);
                                receiverMessage.setReceiver(messageContract.getSenderDisplayName());
                                String stringInformation = messageContract.getInformation();
                                Log.d("Information", " " + stringInformation);
                                Information receive_info = gson.fromJson(stringInformation,Information.class);
                                String receive_text = receive_info.getText();
                                Log.d("Text", "  " + receive_text);
                                receiverMessage.setMessage(receive_text);
                                Message message = handler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putString("title", "hello");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                String groupID = messageContract.getGroupID();
                                Log.d("GroupID", "  " + groupID);

                                String userID = messageContract.getDestUserID();
                               MyRequest changeIDRequest = new MyRequest(Request.Method.GET,STATEURL +  groupID , new Response.Listener<JSONObject>() {
                                   @Override
                                   public void onResponse(JSONObject jsonObject) {
                                       Log.d("onResponse",jsonObject.toString());
                                   }
                               }, new Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError volleyError) {
                                       Log.d("onErrorResponse"," " + volleyError.toString());
                                       Log.d("onErrorResponse","onErrorResponse");
                                   }
                               });
                                requestQueue.add(changeIDRequest);
                                break;
                        }

                    }

                }catch (UnknownHostException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v) {
        final ImMessage imMessage = new ImMessage();
        imMessage.setType(1);
        imMessage.setSender(senderName);
        chat_information =  editText.getText().toString();
        Log.d("chat_information", "  " + chat_information);
        if(TextUtils.isEmpty(chat_information)){
            Log.d("TAG","输入内容为空！");
        }
        else {
            String id = UUID.randomUUID().toString();
            String grouperID = UUID.randomUUID().toString();
            Log.d("UUID","  " + id);
            Log.d("UUIDGROUP"," " + grouperID);
            Information information = new Information();
            information.setId(id);
            information.setText(chat_information);
            String send_information = gson.toJson(information);
            MessageContract messageContract = new MessageContract();
            messageContract.setGroupID(grouperID);
            messageContract.setDestDisplayName(receiverName);
            messageContract.setDestUserID(receiverID);
            messageContract.setSenderDisplayName(senderName);
            messageContract.setSenderUserID(senderID);
            messageContract.setSenderTime(RandString.getTimeStamp());
            messageContract.setInformation(send_information);
            String send_messageContract = gson.toJson(messageContract);
            header.setFragment(send_messageContract);
            header.setInformationType(6);
            final String send_header = gson.toJson(header);
            Log.d("TAG", "  " + send_header);

              //发送http协议的携带信息
        MobileIMHeader mobileIMHeader = new MobileIMHeader();
        mobileIMHeader.setGroupID(grouperID);
        mobileIMHeader.setId(id);
        mobileIMHeader.setMessageContext(chat_information);
        mobileIMHeader.setRecieveUserDisplayName(receiverName);
        mobileIMHeader.setRecieveUserID(receiverID);
        mobileIMHeader.setSenderUserID(senderID);
        mobileIMHeader.setSenderUserDisplayName(senderName);
        mobileIMHeader.setSendDateTime(RandString.getTimeStamp());
        final String string_mobileIMHeader = gson.toJson(mobileIMHeader);
        Log.d("MobileIMHeader", " " + string_mobileIMHeader);
        path = URL + string_mobileIMHeader;
            myJsonRequest = new MyJsonRequest(Request.Method.POST, URL, string_mobileIMHeader, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.d("onResponse","  " +  jsonObject.toString());
                   /* out.println(send_header);
                    out.flush();
                    imMessage.setMessage(chat_information);
                    adapter.addItem(imMessage);
                    editText.setText("");*/

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("onErrorResponse","onErrorResponse");
                }
            });
          requestQueue.add(myJsonRequest);

            out.println(send_header);
            out.flush();
            imMessage.setMessage(chat_information);
            adapter.addItem(imMessage);
            editText.setText("");

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId()){
          case R.id.home:
              finish();
              break;
      }
        return super.onOptionsItemSelected(item);
    }

    public void getUnreandMessage(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(REQUEST_ALL_URL + senderID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("onResponse"," " + jsonArray.toString());
                List<MobileIMHeader> mobileIMHeaderList = gson.fromJson(jsonArray.toString(), new TypeToken<List<MobileIMHeader>>() {
                }.getType());
                Log.d("mobileIMHeaderList"," " + mobileIMHeaderList.size());
                for(int i = 0; i < mobileIMHeaderList.size(); i++){
                    MobileIMHeader mobileIMHeader = mobileIMHeaderList.get(i);
                    String changeID = mobileIMHeader.getGroupID();
                    String receive_text = mobileIMHeader.getMessageContext();
                    ImMessage receive_message = new ImMessage();
                    receive_message.setMessage(receive_text);
                    receive_message.setType(2);
                    receive_message.setReceiver(mobileIMHeader.getRecieveUserDisplayName());
                    adapter.addItem(receive_message);
                    Log.d("TAG", " " + mobileIMHeader.getMessageContext() + mobileIMHeader.getGroupID() + mobileIMHeader.getSendDateTime());
                    MyRequest changeIDRequest = new MyRequest(Request.Method.GET,STATEURL +  changeID , new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("onResponse",jsonObject.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("onErrorResponse"," " + volleyError.toString());
                            Log.d("onErrorResponse","onErrorResponse");
                        }
                    });
                    requestQueue.add(changeIDRequest);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("VolleyError","  " + volleyError.toString());

            }
        });
        requestQueue.add(jsonArrayRequest);
    }




}
