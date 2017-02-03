package biz.markgo.senior_project.registerlogin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Home extends AppCompatActivity {

    EditText home_name, home_password;
    Button home_register,home_login;
    String Name, Password;
    Context ctx=this;
    String NAME="", PASSWORD="", EMAIL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_name = (EditText) findViewById(R.id.home_ETname);
        home_password = (EditText) findViewById(R.id.home_ETpassword);

        home_register=(Button) findViewById(R.id.home_BTregister);
        home_login=(Button) findViewById(R.id.home_BTlogin);

        home_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Register.class);
                startActivity(intent);
            }
        });

        home_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = home_name.getText().toString();
                Password = home_password.getText().toString();
                BackGround b = new BackGround();
                b.execute(Name, Password);

            }
        });


    }
/*
    public void home_register(View v) {
        startActivity(new Intent(this, Register.class));
    }

    public void home_login(View v){
        Name = name.getText().toString();
        Password = password.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name, Password);
    }
*/
    class BackGround extends AsyncTask<String, String, String> {

    private Dialog loadingDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(Home.this, "Please wait", "Loading...");
    }

    @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://senior-project.markgo.biz/account/Login.php");
                String urlParams = "name="+name+"&password="+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }

        }


        @Override
        protected void onPostExecute(String s) {

            loadingDialog.dismiss();
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("member_data");
                NAME = user_data.getString("name");
                EMAIL = user_data.getString("email");

                Intent intent = new Intent(ctx,screen1.class);
                intent.putExtra("name", NAME);
                intent.putExtra("email", EMAIL);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();

                if(NAME=="") {
                    Toast.makeText(getApplicationContext(), "Can not Internet", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}