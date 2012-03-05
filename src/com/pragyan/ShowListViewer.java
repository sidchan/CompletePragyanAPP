package com.pragyan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowListViewer extends ListActivity {

	    private List strings = new ArrayList();
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listviewer);
		    
		    String cat = getIntent().getExtras().getString("type");
		    
		    try{
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
	            
	            if(dataItem.gettype().toLowerCase().trim().equals(cat.toLowerCase().trim())){
			            strings.add(dataItem.getname());
			            
			            /*String FileName=dataItem.getname()+".jpg";
			            File root = Environment.getExternalStorageDirectory();
			            File imgFile = new File(root + "/Pragyan_app/"+FileName);
			            if(imgFile.exists()){
			            	ImageView img=(ImageView) findViewById(R.id.random_image);
			                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			                img.setImageBitmap(myBitmap);            
	            	}*/
	            }
            }
            
            setListAdapter(new ArrayAdapter(this, R.layout.list_item, strings));
         }
		

        catch(Exception e) {
            //Log.v("Error: ",e.getMessage());
            e.printStackTrace();

       }
	 
        final ListView lv=getListView();
        final ShowListViewer contx=this;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent();
				String name=(String) lv.getItemAtPosition(arg2);
				intent.setClass(contx, EventInfoViewer.class);
				intent.putExtra("name",name);
				startActivity(intent);
				
				
			}
        });
	}
		
}
