package xyz.vanluren.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    List<Pair<EditText, String>> params;
    private EditText inputName;
    private EditText profile_text;
    private Button btn_signup;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView linkLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);
        profile_text = (EditText) findViewById(R.id.tell_us_more);

        btn_signup = (Button) findViewById(R.id.btn_signup);
        linkLogin = (TextView) findViewById(R.id.link_login);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://localhost:3000/users";

                String stringEmail = inputEmail.getText().toString();
                String stringPassword = inputPassword.getText().toString();
                String stringName = inputName.getText().toString();


                params = new ArrayList<>();
                params.add(new Pair<>(inputEmail, stringEmail));
                params.add(new Pair<>(inputPassword, stringPassword));
                params.add(new Pair<>(inputEmail, stringEmail));
                params.add(new Pair<>(inputName, stringName));

            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alreadyMember = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(alreadyMember);
            }
        });
    }
}

