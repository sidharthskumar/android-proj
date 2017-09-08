package android.com.stud;


        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.Toast;

public class webview extends AppCompatActivity {
    Button back,refresh;
    WebView w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        w=(WebView)findViewById(R.id.webView);
        w.setWebViewClient(new WebViewClient());
        w.loadUrl("http://www.google.com");
        back=(Button)findViewById(R.id.bck);
        refresh=(Button)findViewById(R.id.rfrsh);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                w.loadUrl("http://www.google.com");
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
                Toast.makeText(webview.this, "sidharth sk productions", Toast.LENGTH_LONG).show();
                return true;
            case R.id.website:
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}
