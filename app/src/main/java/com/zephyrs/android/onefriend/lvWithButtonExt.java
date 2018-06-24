package com.zephyrs.android.onefriend;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Barry on 27/4/17.
 */
// android:src = "@drawable/ic_launcher_round"
public class lvWithButtonExt extends ListActivity {
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exmple_main);

        ListView vncListView = (ListView)findViewById(android.R.id.list);
        back = (Button)findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
        ArrayList<HashMap<String, Object>> remoteWindowItem = new ArrayList<HashMap<String, Object>>();
        int[] dataCell = new int[] {R.id.ItemWinName,R.id.ItemCloseWin};


        SharedPreferences ppstress = getBaseContext().getSharedPreferences("previousstress", 0);
        String allreport = ppstress.getString("allpdf","");
        if(allreport != "") {
            Gson gson = new Gson();
            List<String> allpdf = new ArrayList<>();
            try {
                allpdf = gson.fromJson(allreport, new TypeToken<List<String>>() {
                }.getType());
            } catch (Exception e) {

            }

            for (int i = 0; i < allpdf.size(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemWinName", allpdf.get(i));
                map.put("ItemCloseWin", R.mipmap.cross);
                remoteWindowItem.add(map);
            }
        }

        lvButtonAdapter listItemAdapter = new lvButtonAdapter(
                this,
                remoteWindowItem,
                R.layout.example,


                new String[] {"ItemWinName","ItemCloseWin"},
                new int[] {R.id.ItemWinName,R.id.ItemCloseWin}
        );

        vncListView.setAdapter(listItemAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        l.getItemAtPosition(position);

        SharedPreferences ppstress = this.getSharedPreferences("previousstress", 0);
        String allreport = ppstress.getString("allpdf","");
        Gson gson = new Gson();
        List<String> allpdf = new ArrayList<>();
        try{
            allpdf = gson.fromJson(allreport, new TypeToken<List<String>>() {}.getType());
        }catch (Exception e){
        }
        try
        {
            //Open the PDF
            File root = getFilesDir();
            final File file = new File(root,allpdf.get(position)+".pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri photoURI = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".my.package.name.provider", file);
            intent.setDataAndType(photoURI, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
        catch(Exception e)
        {

        }



    }
}