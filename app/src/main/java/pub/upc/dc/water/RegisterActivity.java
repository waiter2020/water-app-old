package pub.upc.dc.water;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pub.upc.dc.water.bean.User;
import pub.upc.dc.water.utils.Action;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText passwd;
    private EditText passwd2;
    private Button button;
    private Toolbar toolbar;

    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }


    public void initView() {

        toolbar = (Toolbar) findViewById(R.id.tl_head);
        toolbar.setTitle("注册");

        setSupportActionBar(toolbar);


        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        passwd = (EditText) findViewById(R.id.passwd);
        passwd2 = (EditText) findViewById(R.id.passwd2);
        button = (Button) findViewById(R.id.btn_register);
        button.setEnabled(false);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = username.getText().toString();
                boolean matches = text.matches("^[a-zA-Z][a-zA-Z0-9_]{4,15}$");
                if (!matches) {
                    username.setTextColor(Color.rgb(255, 0, 0));
                    a = false;
                    button.setEnabled(false);
                } else {
                    username.setTextColor(Color.rgb(0, 0, 0));
                    a = true;
                    if (b && c && d) {
                        button.setEnabled(true);
                    }
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = email.getText().toString();
                boolean matches = text.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
                if (!matches) {
                    email.setTextColor(Color.rgb(255, 0, 0));
                    b = false;
                    button.setEnabled(false);
                } else {
                    email.setTextColor(Color.rgb(0, 0, 0));
                    b = true;
                    if (a && c && d) {
                        button.setEnabled(true);
                    }
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = phone.getText().toString();
                boolean matches = text.matches("^(1[0-9])\\d{9}$");
                if (!matches) {
                    c = false;
                    button.setEnabled(false);
                    phone.setTextColor(Color.rgb(255, 0, 0));
                } else {
                    phone.setTextColor(Color.rgb(0, 0, 0));
                    c = true;
                    if (a && b && d) {
                        button.setEnabled(true);
                    }
                }
            }
        });

        passwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = passwd2.getText().toString();
                String string = passwd.getText().toString();
                if (!text.equals(string)) {
                    passwd2.setTextColor(Color.rgb(255, 0, 0));
                    d = false;
                    button.setEnabled(false);
                } else {
                    passwd2.setTextColor(Color.rgb(0, 0, 0));
                    d = true;
                    if (a && b && c) {
                        button.setEnabled(true);
                    }
                }
            }
        });

    }


    public void onBtn_registerClick(View view) {

        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPasswd(passwd.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPhoneNumber(phone.getText().toString());
        boolean register = Action.register(user, this);
        if (register) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            intent.putExtra("register", true);
            setResult(1, intent);
            finish();
        } else {
            Toast.makeText(this,"注册失败，请检查输入",Toast.LENGTH_LONG).show();
            setResult(1);
        }
    }


}
