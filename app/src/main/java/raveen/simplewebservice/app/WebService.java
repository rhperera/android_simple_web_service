package raveen.simplewebservice.app;

/**
 * Created by Raveen on 3/21/2015.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;


class WebService  extends AsyncTask<String, Void, String> {

    static InputStream is = null;
    private String Content;
    private String Error = null;
    private ProgressDialog pd;
    private String message;

    public WebService(Activity activity,String m)
    {
        pd = new ProgressDialog(activity);
        message = m;
    }

    protected void onPreExecute() {
        pd.setMessage(message);
        pd.show();
        //add pr-execute function
    }

    // Call after onPreExecute method
    protected String doInBackground(String... params) {
        BufferedReader reader=null;
        try {
            // Defined URL  where to send data
            String url = params[0].toString();
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            for (int i=1;i<params.length-1;i++) {
                pairs.add(new BasicNameValuePair(params[i].toString(),params[i+1].toString()));
            }

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url.toString());
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

            reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "");
            }
            // Append Server Response To Content String
            Content = sb.toString();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            Error = ex.getMessage();
        }
        finally
        {
            try
            {
                reader.close();
            }

            catch(Exception ex) {}
        }
        return Content;
    }

    public void onPostExecute(String result) {

        if(pd.isShowing())
        {
            pd.dismiss();
        }
    }

}

