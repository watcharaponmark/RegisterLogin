package biz.markgo.senior_project.registerlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class screen1 extends AppCompatActivity {

    String name, password, email, Err;
    TextView nameTV, emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        nameTV = (TextView) findViewById(R.id.home_ETname);
        emailTV = (TextView) findViewById(R.id.home_email);


        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        nameTV.setText("Welcome "+name);
        emailTV.setText("Your email is "+email);

    }
}
