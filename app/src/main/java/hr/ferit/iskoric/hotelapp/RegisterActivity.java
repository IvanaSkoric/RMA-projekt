package hr.ferit.iskoric.hotelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register;
    private EditText et_email;
    private EditText et_password;
    private TextView tvLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null)
        {
            finish();
            //profile activity here
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        setUpUI();
    }

    private void setUpUI() {

        et_email= (EditText) findViewById(R.id.et_email);
        et_password= (EditText) findViewById(R.id.et_password);
        btn_register= (Button) findViewById(R.id.btn_register);
        tvLogin= (TextView) findViewById(R.id.textViewLogin);

        btn_register.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v == btn_register)
        {
            registerUser();
        }
        if(v == tvLogin)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }


    }

    private void registerUser() {
        String email=et_email.getText().toString().trim();
        String password=et_password.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please eneter your email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this, "Please eneter your password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        //if email and password are ok, progressbar is shown
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //user is succ registered and logged in, profile activity will start
                            Toast.makeText(RegisterActivity.this, "Registration OK", Toast.LENGTH_SHORT).show();
                            finish();
                                //profile activity here
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else
                        {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        } progressDialog.dismiss();
                    }
                });


    }



}
