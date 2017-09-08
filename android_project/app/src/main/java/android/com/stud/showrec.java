package android.com.stud;


        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.TextView;
        import android.widget.Toast;

public class showrec extends AppCompatActivity {
    TextView name,branch,gender,int1;
    SQLiteDatabase db;
    Cursor record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrec);
        name=(TextView)findViewById(R.id.name);
        branch=(TextView)findViewById(R.id.branch);
        gender=(TextView)findViewById(R.id.gender);
        int1=(TextView)findViewById(R.id.int1);
        db=openOrCreateDatabase("mydatabase",MODE_PRIVATE,null);
        Bundle bundle=getIntent().getExtras();
        String n,b;
        n=bundle.getString("name");
        b=bundle.getString("branch");
        record=db.rawQuery("SELECT * FROM student WHERE name=? AND branch=?",
                new String[]{n,b});
        record.moveToFirst();
        String nm="NAME:        "+record.getString(0);
        name.setText(nm);
        String brnch="BRANCH:   "+record.getString(1);
        branch.setText(brnch);
        String gend="GENDER:   Male";
        if(record.getInt(2)==0)
            gend="GENDER:   Female";
        gender.setText(gend);
        String intrst="";
        if(record.getInt(3)==1)
            intrst=intrst+"Web\n";
        if(record.getInt(4)==1)
            intrst=intrst+"Android\n";
        if(record.getInt(5)==1)
            intrst=intrst+"Design";
        if(intrst==""){
            intrst="None";
        }
        int1.setText(intrst);

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
                Toast.makeText(showrec.this, "sidharth sk productions", Toast.LENGTH_LONG).show();
                return true;
            case R.id.website:
                Intent l = new Intent(showrec.this, webview.class);
                startActivity(l);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}
