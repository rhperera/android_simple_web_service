package raveen.simplewebservice.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView response_text = (TextView)findViewById(R.id.response);
        final TextView post_text = (TextView)findViewById(R.id.post_text);
        final TextView url_text = (TextView)findViewById(R.id.url_text);
        Button button = (Button)findViewById(R.id.button);
        final String url = "http://ip.jsontest.com/";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebService webService = new WebService(MainActivity.this,"Retrieving your data....");
                try {
                    String response = webService.execute(url).get().toString();
                    response_text.setText(response);
                    url_text.setText(url);
                    post_text.setText("None");
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
