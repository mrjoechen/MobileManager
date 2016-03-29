package com.example.chenqiao.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.chenqiao.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHENQIAO on 2016/3/29.
 */
public class ContactUtils {

    public static List<Contact> getAllContact(Context ctx){


        List<Contact> contactslist = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                new String[]{"contact_id"}, null, null, null);
        while (cursor.moveToNext()) {

            int contact_id = cursor.getInt(0);
            if (contact_id==0) {
                continue;
            }
            Log.i("show one contact", contact_id + "");
            Cursor cursor2 =contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                    new String[]{"data1","mimetype"}, "raw_contact_id=?", new String[]{""+contact_id}, null);

            Contact onecontact = new Contact();

            while (cursor2.moveToNext()) {

                String data1 = cursor2.getString(0);
                String mimetype = cursor2.getString(1);

                if ("vnd.android.cursor.item/name".equals(mimetype)){
                    onecontact.setName(data1);
                }else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                    onecontact.setNumber(data1);
                }
            }
            Log.i("show one contact", onecontact.toString());

            contactslist.add(onecontact);

        }

        return contactslist;

    }

}
