package android.com.stud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    ListView l;
    ArrayList<String> sl=new ArrayList<>();
    ArrayAdapter studapter;
    Button AddStudent;
    Cursor menuset;
    SQLiteDatabase db;
    Long clicks;
    Boolean dupe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=openOrCreateDatabase("mydatabase",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student("+
                "name TEXT NOT NULL,"+
                "branch TEXT NOT NULL,"+
                "gend INT NOT NULL CHECK(gend IN (0,1)),"+
                "web INT NOT NULL CHECK(web IN (0,1)),"+
                "android INT NOT NULL CHECK(android IN (0,1)),"+
                "design INT NOT NULL CHECK(design IN (0,1)));");
        menuset=db.rawQuery("SELECT name,branch FROM student",null);
        menuset.moveToFirst();
        while(!menuset.isAfterLast()){
            String n,b;
            n=menuset.getString(0);
            b=menuset.getString(1);
            sl.add(n+"\n"+b);
            menuset.moveToNext();
        }
        l=(ListView)findViewById(R.id.studlist);
        studapter=new ArrayAdapter<>(this,R.layout.studlist,sl);
        l.setAdapter(studapter);
        AddStudent=(Button)findViewById(R.id.AddStud);
        long clicks=0;
        boolean dupe=false;
        AddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(MainActivity.this, Main2Activity.class);
                startActivityForResult(m,1);
            }
        });
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s=((TextView) view).getText().toString();
                String[] nb=s.split("\n",2);
                String n,b;
                n=nb[0];
                b=nb[1];
                Intent recshow = new Intent(MainActivity.this,showrec.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",n);
                bundle.putString("branch",b);
                recshow.putExtras(bundle);
                startActivity(recshow);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1 && resultCode==RESULT_OK){
            Long clicks=data.getLongExtra("clicks",0);
            Boolean dupe=data.getBooleanExtra("dupe",false);
            Snackbar.make(AddStudent,"Submit has been clicked "+clicks+" times",Snackbar.LENGTH_SHORT).show();
            if(dupe) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Submission failed!");
                builder.setMessage("Record already exists");
                builder.setCancelable(true);
                builder.show();
            }
            else{
                Toast.makeText(MainActivity.this,"Refreshing list...please wait",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }, 2000);
            }

        }



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.credits:
                Toast.makeText(MainActivity.this, "sidharth sk productions", Toast.LENGTH_LONG).show();
                return true;
            case R.id.website:
                Intent l = new Intent(MainActivity.this, webview.class);
                startActivity(l);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
    boolean backPressed=false;

    @Override
    public void onBackPressed() {
        if (backPressed) {
            System.exit(1);
        } else {
            int timeMillis = 2000;
            Snackbar.make(AddStudent,"Tap again to exit",
                    Snackbar.LENGTH_SHORT).show();
            backPressed = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            }, timeMillis);
        }

    }

}