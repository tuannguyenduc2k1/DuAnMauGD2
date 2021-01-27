package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.DBUser.User;
import com.example.myapplication.dao.NguoiDungDAO;
import com.example.myapplication.model.NguoiDung;

import java.util.ArrayList;

import static android.content.Intent.EXTRA_USER;

public class LoginScreen extends AppCompatActivity {
    EditText edUserName, edPassword;
    Button btnLogin, btn_SugnUp,btn_forgotPassWord;
    CheckBox chkRememberPass;
    String strUser, strPass;
    NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* ull mang hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login_screen);

        edUserName = (EditText) findViewById(R.id.username);
        edPassword = (EditText) findViewById(R.id.pass);
        btnLogin = (Button) findViewById(R.id.login);
        btn_SugnUp = (Button)findViewById(R.id.SignUp);
        btn_forgotPassWord = (Button)findViewById(R.id.quenmk);
        chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);
        nguoiDungDAO = new NguoiDungDAO(LoginScreen.this);

        final ImageView libraryicon= (ImageView) findViewById(R.id.iconlibrary);

        libraryicon.setImageResource(R.drawable.icons8_library);


    }

    public void checkLogin(View v){
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.isEmpty()||strPass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Tên đăng nhập và mật khẩu không được bỏ trống",
                    Toast.LENGTH_SHORT).show();
        }else {
            if (nguoiDungDAO.checkLogin(strUser,strPass)>0){
                Toast.makeText(getApplicationContext(),"Xin Chào ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
            }
            if
            (strUser.equalsIgnoreCase("admin")&&strPass.equalsIgnoreCase("admin")){
                rememberUser(strUser,strPass,chkRememberPass.isChecked());
                Toast.makeText(getApplicationContext(),"Login thành công",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Tên đăng nhập và mật khẩu không đúng",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status){
            //xoa tinh trang luu tru truoc do
            edit.clear();
        }else {
            //luu du lieu
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            edit.putBoolean("REMEMBER",status);
        }
        //luu lai toan bo
        edit.commit();
    }
    public void Sigupnow(View view){
        Intent intent = new Intent(LoginScreen.this,SignUpScreen.class);
        startActivity(intent);
    }
    public void Forgot_Pass(View view){
        Intent intent = new Intent(LoginScreen.this,ForgotPassword.class);
        startActivity(intent);
    }

}
