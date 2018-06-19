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

public class SignInActivity extends AppCompatActivity {
    private Button signinButton;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signinButton = (Button) findViewById(R.id.register_button);
        signinButton.setOnClickListener(new SignInClick());

        nameEditText = (EditText) findViewById(R.id.name_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirm_password_editText);

        passwordEditText.setTypeface(Typeface.DEFAULT);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());

        confirmPasswordEditText.setTypeface(Typeface.DEFAULT);
        confirmPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
    }

    private class SignInClick implements View.OnClickListener {
        public void onClick(View v) {
            if (v == signinButton){
                if(checkFields() && checkPassword()) {
                    User newUser = createUser();
                    AccountManagerInterface accountManager = new SimpleAccountManager();
                    boolean success = accountManager.signUp(newUser);
                    if(success) {
                        Toast.makeText(SignInActivity.this, "Successfully signed up!", Toast.LENGTH_SHORT).show();
                        ((MESystemApplication) SignInActivity.this.getApplication())
                                .setCurrentUser(accountManager.getCurrentAccountUser());
                        Intent intent = new Intent(SignInActivity.this, ChatListActivity.class);
                        SignInActivity.this.startActivity(intent);
                        finish();
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
            if(confirmPasswordEditText.getText().toString().equals("")){
                nameEditText.setError("Please, confirm password");
                isOK = false;
            }
            return isOK;
        }

        private boolean checkPassword() {
            if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString()))
                return true;
            Toast.makeText(SignInActivity.this, "Password and confirmation password are not equal", Toast.LENGTH_LONG).show();
            return false;
        }

        private User createUser() {
            User user = new User();
            user.setName(nameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            return user;
        }
    }
}
