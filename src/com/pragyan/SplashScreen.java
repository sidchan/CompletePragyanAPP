package com.pragyan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.content.Intent;
import android.content.res.AssetManager;

public class SplashScreen extends Activity {
	/** Called when the activity is first created. */
	
	private Thread mSplashThread;
	private MediaPlayer splashsound;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.splash);
	    final SplashScreen sPlashScreen=this;
	    
	    mSplashThread = new Thread(){
	    	@Override
	    	public void run(){
	    		try {
	    			synchronized (this) {
						wait(5000);
	    			}
	    		}
	    		catch (InterruptedException ex) {
					// TODO: handle exception
				}	
	    		finish();
	    		
	    		Intent intent=new Intent();
	    		intent.setClass(sPlashScreen, EventDayScreen.class);
	    		startActivity(intent);
	    		stop();
	    		
	    	}
	    };
	    mSplashThread.start();
	    synchronized (mSplashThread) {
	    	File upd= new File(Environment.getExternalStorageDirectory()+"/Pragyan_app/updates.xml");
	    	if (!upd.exists()){
	    	CopyAssets();
	    	}
		}
	    synchronized(mSplashThread){
	    	splashsound = MediaPlayer.create(this,R.raw.logo_noise);
	        splashsound.start();
	    }
	    
	    
	}
	private void CopyAssets() {
		
	    AssetManager assetManager = getAssets();
	    String[] files = null;
	    try {
	        files = assetManager.list("");
	    } catch (IOException e) {
	        Log.e("tag", e.getMessage());
	    }
	    for(String filename : files) {
	        InputStream in = null;
	        OutputStream out = null;
	        try {
	          in = assetManager.open(filename);
	          new File("/mnt/sdcard/Pragyan_app/").mkdirs();
	          new File("/mnt/sdcard/Pragyan_app/images/").mkdirs();
	          new File("/mnt/sdcard/Pragyan_app/sponsors/").mkdirs();
	          out = new FileOutputStream("/mnt/sdcard/Pragyan_app/" + filename);
	          copyFile(in, out);
	          in.close();
	          in = null;
	          out.flush();
	          out.close();
	          out = null;
	        } catch(Exception e) {
	            Log.e("tag", e.getMessage());
	        }       
	    }
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}



}