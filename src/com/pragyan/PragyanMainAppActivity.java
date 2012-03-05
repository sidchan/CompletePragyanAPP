package com.pragyan;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class PragyanMainAppActivity extends Activity implements android.view.View.OnClickListener{
    Date now;
	CharSequence tdy;
	Date dest;
	CharSequence pragyan;
	long diffInMis;
	TextView dtv;
	TextView htv;
	TextView mtv;
	TextView stv;
	public PragyanMainAppActivity prag=this;
	public int currentimageindex=0;
	private int[] IMAGE_IDS = {
			R.drawable.splash0, R.drawable.splash1, R.drawable.splash2,	
			R.drawable.splash3
		};
	/** Called when the activity is first created. */
    @Override
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutInflater().inflate(R.layout.main,null));
        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
            	
            	AnimateandSlideShow();
            	
            }
        };
        int delay = 1000; // delay for 1 sec.

        int period = 8000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

       	 	public void run() {

       	 			mHandler.post(mUpdateResults);

       	 	}

        }, delay, period);
     	

    }
private void AnimateandSlideShow() {
     	
     	ImageView slidingimage = (ImageView) findViewById(R.id.idi);
    		slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
    		
    		currentimageindex++;
     }
     
    public void onStart(){
    	super.onStart();
    	dtv=(TextView) this.findViewById(R.id.days_count_view);
    	htv=(TextView) this.findViewById(R.id.hr_count_view);
    	mtv=(TextView) this.findViewById(R.id.min_count_view);
    	stv=(TextView) this.findViewById(R.id.sec_count_view);
    	startCountDown();
    	startMenuAnim();
    }
    
    public void setClickListeners(){
    	ImageView top_btn=(ImageView)findViewById(R.id.top_btn);
    	ImageView bottom_btn=(ImageView)findViewById(R.id.bottom_btn);
    	ImageView right_btn=(ImageView)findViewById(R.id.pivotImg);
    	ImageView left_btn=(ImageView)findViewById(R.id.left_btn);
    	top_btn.setOnClickListener(this);
    	bottom_btn.setOnClickListener(this);
    	left_btn.setOnClickListener(this);
    	right_btn.setOnClickListener(this);
    }
    
    public void startMenuAnim(){
    	Animation rotateMenu = AnimationUtils.loadAnimation(this, R.anim.main_rotate);
    	LayoutAnimationController rotController = new LayoutAnimationController(rotateMenu, 0);
    	FrameLayout menuWheelLayout = (FrameLayout)findViewById(R.id.menuLayout);
    	AnimListener animlisten = new AnimListener();
    	rotateMenu.setAnimationListener(animlisten);
    	menuWheelLayout.setLayoutAnimation(rotController);
    
    }
    
    public void startCountDown(){
    	now = new Date();
        tdy= DateFormat.format("yyyy-MM-dd hh:mm:ss", now.getTime());
        int year=112;
        int month=01;
        int date=22;
        int hour=00;
        int min=00;
        int sec=00;	
        dest = new Date(year,month,date,hour,min,sec);
        pragyan= DateFormat.format("yyyy-MM-dd hh:mm:ss", dest.getTime());
        diffInMis= dest.getTime() - now.getTime();
        Prag counter=new Prag(diffInMis,1000);

        counter.start();
    }
    
    

    
	public class Prag extends CountDownTimer {
		long days,rem,hours,rem2,mins,secs;
		public Prag(long millisInFuture,long countDownInterval){
			super(millisInFuture,countDownInterval);
		}
		public void onFinish(){
	//		tv.setText("Prayan is here !!!");
		}
		public void onTick(long millisUntilFinished){
			millisUntilFinished=millisUntilFinished/1000;
			days= millisUntilFinished/86400;
			rem=millisUntilFinished%86400;
			
			hours=rem/3600;
			rem2=rem%3600;
			mins=rem2/60;
			secs=rem2%60;
			setTexts();
			//Log.v("damn",String.valueOf(days));

		}
		public void setTexts(){
			dtv.setText(String.valueOf(days));
			htv.setText(String.valueOf(hours));
			mtv.setText(String.valueOf(mins));
			stv.setText(String.valueOf(secs));
		}
		
	}
	
	
	public class AnimListener implements AnimationListener{
		
		public void onAnimationStart(Animation animation) {
			
		}

        public void onAnimationEnd(Animation animation) 
        {
        	Intent intent=new Intent(prag,sid.class);
        	startActivity(intent);
        	Log.v("done","animation done");
        	setClickListeners();
        	
        }

        public void onAnimationRepeat(Animation animation) {
        }
	}
	
	
	public void onClick(View v){
		String type="";
		if(v.getId()==R.id.bottom_btn){
			
			Log.d("ClickEvent","botoom map clicked");
		}
		if(v.getId()==R.id.top_btn){
			type="guest lecture";
			Log.d("ClickEvent","top map clicked");
		}
		if(v.getId()==R.id.left_btn){
			type="workshop";
			Log.d("ClickEvent","left ap clicked");
		}
		if(v.getId()==R.id.pivotImg){
			type="events";
			Log.d("ClickEvent","right map clicked");
		}
		Intent inte = new Intent();
		inte.putExtra("type", type);
		inte.setClass(this,ShowListViewer.class);
		startActivity(inte);
	}
	
}