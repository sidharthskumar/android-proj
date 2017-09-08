package android.com.stud;


        import android.content.Intent;
        import android.content.SharedPreferences;
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
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.Spinner;
        import android.widget.Toast;

        import java.sql.SQLClientInfoException;
        import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    RadioGroup g;
    RadioButton gselected;
    EditText t1;
    Button b1;
    CheckBox web,android,design;
    Spinner drpdwn;
    SQLiteDatabase db;
    SharedPreferences studsp;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        studsp=getSharedPreferences("studentappsp",MODE_PRIVATE);
        if(!studsp.contains("register_count")){
            ed=studsp.edit();
            ed.putLong("register_count",0);
            ed.apply();
        }
        t1=(EditText)findViewById(R.id.t1);
        b1=(Button)findViewById(R.id.b1);
        g=(RadioGroup)findViewById(R.id.radioGroup);
        web=(CheckBox)findViewById(R.id.web);
        android=(CheckBox)findViewById(R.id.android);
        design=(CheckBox)findViewById(R.id.design);
        drpdwn=(Spinner)findViewById(R.id.drop);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branches_array,R.layout.droplist);
        adapter.setDropDownViewResource(R.layout.droplist);
        drpdwn.setAdapter(adapter);
        g.check(R.id.rbMale);
        db=openOrCreateDatabase("mydatabase",MODE_PRIVATE,null);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dupe=false;
                ed=studsp.edit();
                ed.putLong("register_count",studsp.getLong("register_count",0)+1);
                ed.apply();
                long clicks=studsp.getLong("register_count",0);
                String name=t1.getText().toString();
                String branch=drpdwn.getSelectedItem().toString();
                int radioid=g.getCheckedRadioButtonId()==R.id.rbMale?1:0;
                int wb,andr,des;
                wb=web.isChecked()?1:0;
                andr=android.isChecked()?1:0;
                des=design.isChecked()?1:0;
                Cursor resultSet=db.rawQuery("SELECT * FROM student WHERE name=?"+
                        " AND branch=?",new String[]{name,branch});
                resultSet.moveToFirst();
                if(resultSet.isAfterLast()) {
                    db.execSQL("INSERT INTO student VALUES('"+name+"','"+branch+"',"+radioid+","+
                            wb+","+andr+","+des+");");
                }
                else{
                    dupe=true;
                }
                resultSet.close();
                Intent l = new Intent();
                l.putExtra("clicks",clicks);
                l.putExtra("dupe",dupe);
                setResult(Main2Activity.RESULT_OK,l);
                finish();

            }
        });
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
                Toast.makeText(Main2Activity.this, "sidharth sk productions", Toast.LENGTH_LONG).show();
                return true;
            case R.id.website:
                Intent l = new Intent(Main2Activity.this, webview.class);
                startActivity(l);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}


