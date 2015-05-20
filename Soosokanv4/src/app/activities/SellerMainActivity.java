package app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.entities.SysApplication;

import com.example.soosokanv3.R;


public class SellerMainActivity extends Activity implements OnClickListener{
	 Intent intent;
	
	 SharedPreferences sp;
	 
	 public static final String TAG = "SELLERMAIN";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		sp = this.getSharedPreferences("userInfo", 0);
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		setContentView(R.layout.activity_seller_main);
		
		Button btn_manage_item = (Button)findViewById(R.id.Manage_item_button);
		Button btn_manage_ads = (Button)findViewById(R.id.Manage_ads_button);
		Button btn_manage_account = (Button)findViewById(R.id.Manage_account_button);
		
		String account = sp.getString("NAME", "");

		TextView accountname = (TextView)findViewById(R.id.seller_accountname);
		accountname.setText(account);
		
		String address = sp.getString("ADDRESS", "");

		TextView addressname = (TextView)findViewById(R.id.seller_address);
		addressname.setText(address);
		
		
		
		TextView itemno = (TextView)findViewById(R.id.itemno);
		itemno.setText("1000");
		
		TextView adsno = (TextView)findViewById(R.id.adsno);
		adsno.setText("30");
		
		
		
		btn_manage_item.setOnClickListener(this);
		btn_manage_ads.setOnClickListener(this);
		btn_manage_account.setOnClickListener(this);		
	}
	
	 
		 public void onClick(View v) {
		  // TODO Auto-generated method stub
		 
		  switch(v.getId()){
		  case R.id.Manage_item_button:
			 intent = new Intent (SellerMainActivity.this, ItemManageActivity.class);
			 startActivity(intent);
			 break;
			 
		  case R.id.Manage_ads_button:
			  intent = new Intent(SellerMainActivity.this, AdsManageActivity.class);
			  startActivity(intent);
			  break;
			  
		  case R.id.Manage_account_button:
			  intent = new Intent(SellerMainActivity.this, AccountManageActivity.class);
			  startActivity(intent);
			  break;
		 
			  default:
				  break;
			  
		  }
		  
		 }
		 
		 
	
	

}
