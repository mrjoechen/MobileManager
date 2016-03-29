package com.example.chenqiao.mobilemanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chenqiao.application.MyApplication;

public class Setup3Activity extends BaseActivity {


    private EditText et_setup3_safenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        getSupportActionBar().hide();
        et_setup3_safenum = (EditText) findViewById(R.id.et_setup3_safenum);
    }


    //获取联系人：
    //方法1，跳到系统选择联系人的页面去选择
/*    public void selectcontact(View v){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, 100);
    }*/

    //方法2，跳到系统选择联系人的页面去选择
    public void selectcontact(View v){

        startActivityForResult(new Intent(this, ContactListActivity.class), 200);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode==RESULT_OK){

            if (requestCode==100){

                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                et_setup3_safenum.setText(number);

            }
        }
        else if (resultCode ==1000){

            if (requestCode==200){

                String number =  data.getStringExtra("number");
                et_setup3_safenum.setText(number);

            }

        }


    }

    public void previous(View v){
        startActivity(new Intent(this,Setup2Activity.class));
        overridePendingTransition(R.anim.slideoutright, R.anim.slideinleft);


    }

    public void next(View v){

        final String s = et_setup3_safenum.getText().toString();
        if (!s.isEmpty()){

            MyApplication.saveConfig("safenum", s);
            //
            startActivity(new Intent(this,Setup4Activity.class));
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);


        }else{
            Toast.makeText(Setup3Activity.this, "安全号码不能为空！", Toast.LENGTH_SHORT).show();
        }


    }
}
