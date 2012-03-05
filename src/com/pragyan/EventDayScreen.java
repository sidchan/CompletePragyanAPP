package com.pragyan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDayScreen extends Activity {

	private EventDayScreen eventdayscreen;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	
		setContentView(R.layout.eventday);
		TextView des=(TextView) findViewById(R.id.random_desc);
		des.setMovementMethod(new ScrollingMovementMethod());
		
		try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
			ExampleHandler myExampleHandler = new ExampleHandler();
			xr.setContentHandler(myExampleHandler);
			File xml= new File(Environment.getExternalStorageDirectory()+"/Pragyan_app/updates.xml");
            sp.parse(xml,myExampleHandler);
            List DataSet = myExampleHandler.getData();
            Collections.shuffle(DataSet);
            ParsedExampleDataSet dataItem ;
            Iterator j;
            j = DataSet.iterator();
            dataItem= (ParsedExampleDataSet) j.next();
            TextView tit=(TextView) findViewById(R.id.random_title);
            
            tit.setText(dataItem.getname());
            des.setText(dataItem.getdescription());
            Log.v("asdasd",dataItem.getname());
            Log.v("asdasd",dataItem.getdescription());
            String FileName=dataItem.getname()+".jpg";
            File root = Environment.getExternalStorageDirectory();
            File imgFile = new File(root + "/Pragyan_app/"+FileName);
            if(imgFile.exists()){
            	ImageView img=(ImageView) findViewById(R.id.random_image);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);            
            }
           }       

        catch (Exception e) {
            Log.v("Error: ",e.getMessage());
            e.printStackTrace();

       }
	
	
	
	
	
	}
		
	@Override
	public boolean onTouchEvent(MotionEvent evt){
		if (evt.getAction()==MotionEvent.ACTION_DOWN){
			Log.v("damn","inside");
			Intent intent = new Intent();
			intent.setClass(this, PragyanMainAppActivity.class);
			startActivity(intent);
		}
		return true;
	}

}
