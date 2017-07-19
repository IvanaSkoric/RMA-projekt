package hr.ferit.iskoric.hotelapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView tvUserEmail;
    private Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }



        setUpUI();
    }

    private void setUpUI() {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        tvUserEmail= (TextView) findViewById(R.id.tvUserEmail);
        btnLogout= (Button) findViewById(R.id.btnLogout);
        tvUserEmail.setText("Welcome " +user.getEmail());
        btnLogout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==btnLogout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

    }
}
