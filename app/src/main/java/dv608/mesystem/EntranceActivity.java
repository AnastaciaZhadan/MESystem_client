package dv608.mesystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EntranceActivity extends AppCompatActivity {
    private Button signinButton;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        signinButton = (Button) findViewById(R.id.sign_in_button);
        signinButton.setOnClickListener(new EntranceClick());

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new EntranceClick());
    }

    private class EntranceClick implements View.OnClickListener {
        public void onClick(View v) {
            if (v == signinButton){
                Intent intent = new Intent(EntranceActivity.this, SignInActivity.class);
                EntranceActivity.this.startActivity(intent);
            }
            else if (v == loginButton){
                Intent intent = new Intent(EntranceActivity.this, LoginActivity.class);
                EntranceActivity.this.startActivity(intent);
            }
            finish();
        }
    }
}
