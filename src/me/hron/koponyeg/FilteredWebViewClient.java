/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package me.hron.koponyeg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 * @author hron
 */
public class FilteredWebViewClient extends WebViewClient {

    private String host;
    private Activity parent;

    public FilteredWebViewClient(String host, Activity parent) {
        this.host = host;
        this.parent = parent;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri u = Uri.parse(url);
        if(u.getHost().contentEquals(host)) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_VIEW, u);
        parent.startActivity(i);
        return true;
    }


}
