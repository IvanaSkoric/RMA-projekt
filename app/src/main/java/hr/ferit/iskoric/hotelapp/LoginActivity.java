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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnSignin;
    private EditText et_email;
    private EditText et_password;
    private TextView tvRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null)
        {
            finish();
            //profile activity here
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        et_email= (EditText) findViewById(R.id.et_email);
        et_password= (EditText) findViewById(R.id.et_password);
        btnSignin= (Button) findViewById(R.id.btn_register);
        tvRegister= (TextView) findViewById(R.id.tvRegister);

        btnSignin.setOnClickListener(this);
//        tvRegister.setOnClickListener(this);}



    public void onClick(View v) {

        if (v==btnSignin)
        {
            userLogin();
        }

        /*if(v==tvRegister)
        {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }*/

    }

    private void userLogin() {

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
        progressDialog.setMessage("Loggin User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            finish();
                            //profile activity here
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }

                    }
                });



    }
}
