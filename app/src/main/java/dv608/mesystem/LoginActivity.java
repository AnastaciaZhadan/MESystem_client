package dv608.mesystem;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import auxilary.MESystemApplication;
import auxilary.models.User;
import businesslogic.AccountManagerInterface;
import businesslogic.SimpleAccountManager;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText nameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.proceedLogin_button);
        loginButton.setOnClickListener(new LoginClick());

        nameEditText = (EditText) findViewById(R.id.loginName_editText);
        passwordEditText = (EditText) findViewById(R.id.loginpass_editText);

        passwordEditText.setTypeface(Typeface.DEFAULT);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
    }

    private class LoginClick implements View.OnClickListener {
        public void onClick(View v) {
            if (v == loginButton){
                if(checkFields()) {
                    User user = createUser();
                    AccountManagerInterface accountManager = new SimpleAccountManager();
                    boolean success = accountManager.login(user);
                    if(success) {
                        Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                        ((MESystemApplication) LoginActivity.this.getApplication())
                                .setCurrentUser(accountManager.getCurrentAccountUser());
                        Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
                        LoginActivity.this.startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(LoginActivity.this, "Cannot process to log in!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        private boolean checkFields() {
            boolean isOK = true;
            if(nameEditText.getText().toString().equals("")){
                nameEditText.setError("Please, enter name");
                isOK = false;
            }
            if(passwordEditText.getText().toString().equals("")){
                passwordEditText.setError("Please, enter password");
                isOK = false;
            }
            return isOK;
        }

        private User createUser() {
            User user = new User();
            user.setName(nameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            return user;
        }
    }
}
