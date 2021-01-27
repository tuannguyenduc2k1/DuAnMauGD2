package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.ForgotPassword;
import com.example.myapplication.LoginScreen;
import com.example.myapplication.R;
import com.example.myapplication.SignUpScreen;
import com.example.myapplication.adapter.NguoiDungAdapter;
import com.example.myapplication.dao.NguoiDungDAO;
import com.example.myapplication.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ListNguoiDungMainActivity extends AppCompatActivity {
    public static List<NguoiDung> dsNguoiDung = new ArrayList<>();
    ListView lvNguoiDung;
    NguoiDungAdapter adapter = null;
    NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi_dung_main);
        setTitle("NGƯỜI DÙNG");
        lvNguoiDung = (ListView) findViewById(R.id.listviewNguoiDung);
        nguoiDungDAO = new NguoiDungDAO(ListNguoiDungMainActivity.this);
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter = new NguoiDungAdapter(this,dsNguoiDung);
        lvNguoiDung.setAdapter(adapter);
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new
                        Intent(ListNguoiDungMainActivity.this,NguoiDungdetaiActivity.class);
                Bundle b = new Bundle();
                b.putString("USERNAME", dsNguoiDung.get(position).getUserName());
                b.putString("PHONE", dsNguoiDung.get(position).getPhone());
                b.putString("FULLNAME", dsNguoiDung.get(position).getHoTen());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        lvNguoiDung.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        dsNguoiDung.clear();
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter.changeDataset(nguoiDungDAO.getAllNguoiDung());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.add:
                intent = new
                        Intent(ListNguoiDungMainActivity.this,NguoiDungActivity.class);
                startActivity(intent);
                return(true);
            case R.id.btnChangePass:
                intent = new
                        Intent(ListNguoiDungMainActivity.this, ForgotPassword.class);
                startActivity(intent);
                return(true);
            case R.id.btnLogout:
                SharedPreferences pref =
                        getSharedPreferences("USER_FILE",MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                //xoa tinh trang luu tru truoc do
                edit.clear();
                edit.commit();
                intent = new Intent(ListNguoiDungMainActivity.this, LoginScreen.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}