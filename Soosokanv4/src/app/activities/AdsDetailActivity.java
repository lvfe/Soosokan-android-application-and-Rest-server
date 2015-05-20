package app.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import app.entities.SysApplication;

import com.example.soosokanv3.R;

public class AdsDetailActivity extends Activity {
	public static final String TAG = "ADSDETAIL";
	private TextView adstime,adstitle,adsdes;
	private String adstimeString,adstitleString,adsdesString;
	public AdsDetailActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.addActivity(this, TAG);
        setContentView(R.layout.activity_ads_detail);
        Intent intent = getIntent();
        
        String id = intent.getStringExtra("adsId");
        //String sellerId = intent.getStringExtra("sellerId");
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
       // String time = intent.getStringExtra("time");
        String distance = intent.getStringExtra("distance");
        String attribute = intent.getStringExtra("attribute");
         
        
        adstime=(TextView)findViewById(R.id.adstime);
        adstitle=(TextView)findViewById(R.id.adstitle);
        adsdes=(TextView)findViewById(R.id.adsdes);
        
       // adstime.setText(time);
        adstitle.setText(title);
        adsdes.setText(description);

    }

}
