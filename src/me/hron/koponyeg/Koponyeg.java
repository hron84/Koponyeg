package me.hron.koponyeg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Koponyeg extends Activity {

    WebView wvMain;
    /** Called when the activity is first created. */
    private final String BASEURL = "http://m.koponyeg.hu/iphone.php";
    private String url = BASEURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Activity me = this;
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        setProgressBarVisibility(true);
        setContentView(R.layout.main);
        setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON, R.drawable.icon);
        
        try {
            wvMain = (WebView) findViewById(R.id.wvMain);
            wvMain.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    // TODO Auto-generated method stub
                    me.setProgress(newProgress * 100);
                }
            });
            wvMain.getSettings().setJavaScriptEnabled(true);
            wvMain.setWebViewClient(new FilteredWebViewClient("m.koponyeg.hu", this));
        } catch (Exception ex) {
            logException(ex);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPrefs();
        wvMain.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            this.finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            wvMain.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences:
                try {
                    logDebug("Prepare preferences");
                    Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
                    logDebug("Start preferences");
                    startActivity(settingsActivity);
                    logDebug("End preferences");
                    return true;
                } catch (Exception ex) {
                    logException(ex);
                }
            case R.id.quit:
                Log.i(this.getClass().getSimpleName(), "Exiting");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String town = prefs.getString("town", "Budapest");
        if (town.length() > 1) {
            url = BASEURL + "?q=" + Uri.encode(town);
        }
    }

    private void logException(Exception ex) {
        StackTraceElement[] trace = ex.getStackTrace();
        String msg = ex.getMessage() + "\n";
        for (StackTraceElement t : trace) {
            msg += t.toString() + "\n";
        }
        Log.e(this.getClass().getSimpleName(), msg);
    }

    private void logDebug(String s) {
        Log.d(this.getClass().getSimpleName(), s);
    }
}
