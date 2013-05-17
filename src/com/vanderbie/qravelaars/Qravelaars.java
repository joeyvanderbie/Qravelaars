package com.vanderbie.qravelaars;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Qravelaars extends Activity {
	private WebView myWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qravelaars);
		
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/texgyreadventor-bold-webfont.ttf");
	    TextView myTextView = (TextView)findViewById(R.id.h1);
	    myTextView.setTypeface(myTypeface);
		
	    
		
		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new MyWebViewClient());
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		myWebView.loadUrl("http://www.qravel.nl/qravelaars");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // Check if the key event was the Back button and if there's history
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
	        myWebView.goBack();
	        return true;
	    }
	    // If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return super.onKeyDown(keyCode, event);
	}
	
	private class MyWebViewClient extends WebViewClient {
	    private boolean firstTime = true;
		@Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(url.contains("mailto") || url.contains("tel")){

			}else if (Uri.parse(url).getHost().equals("www.qravel.nl")) {
	            // This is my web site, so do not override; let my WebView load the page
	        	if(Uri.parse(url).getPathSegments().contains("compontents") || Uri.parse(url).getPathSegments().contains("qravelaars")){
	        		return false;
	        	}
	        }
	        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
	        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	        startActivity(intent);
	        return true;
	    }
        @Override
        public void onPageFinished(WebView view, String url) {
        	if(firstTime){
	        	RelativeLayout rt = (RelativeLayout) findViewById(R.id.splash);
	        	rt.setVisibility(RelativeLayout.GONE);
	            myWebView.loadUrl("http://www.qravel.nl/qravelaars");
	            myWebView.setVisibility(myWebView.VISIBLE);
	            firstTime = !firstTime;
        	}
        }
	}
}
