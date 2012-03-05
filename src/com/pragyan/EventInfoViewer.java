package com.pragyan;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class EventInfoViewer extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.eventinfoviewer);
	    TextView des=(TextView) findViewById(R.id.info_desc);
		des.setMovementMethod(new ScrollingMovementMethod());
		
	    String typename=getIntent().getExtras().getString("name");
	    TextView tv = (TextView) findViewById(R.id.info_title);
	    tv.setText(typename);
	    try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
			ExampleHandler myExampleHandler = new ExampleHandler();
			xr.setContentHandler(myExampleHandler);
			File xml= new File(Environment.getExternalStorageDirectory()+"/Pragyan_app/updates.xml");
            sp.parse(xml,myExampleHandler);
            List DataSet = myExampleHandler.getData();
            ParsedExampleDataSet dataItem ;
            Iterator j;
            j = DataSet.iterator();
            while(j.hasNext()){
           
	            dataItem= (ParsedExampleDataSet) j.next();
	            if(dataItem.getname().toLowerCase().trim().equals(typename.toLowerCase().trim())){
			            TextView tit=(TextView) findViewById(R.id.info_title);
			            tit.setText(dataItem.getname());
			            des.setText(dataItem.getdescription());
			            /*String FileName=dataItem.getname()+".jpg";
			            File root = Environment.getExternalStorageDirectory();
			            File imgFile = new File(root + "/Pragyan_app/"+FileName);
			            if(imgFile.exists()){
			            	ImageView img=(ImageView) findViewById(R.id.random_image);
			                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			                img.setImageBitmap(myBitmap);            
		            	}*/
            }}
           }       

        catch (Exception e) {
            Log.v("Error: ",e.getMessage());
            e.printStackTrace();

       }
	    
	    // TODO Auto-generated method stub
	}

}
