package app.activities;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import app.Entity.NetworkProperties;
import app.Entity.SysApplication;

import com.example.soosokanfu.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class AdsMainListActivity extends Activity implements
		OnChildClickListener {
	private static final String SERVICE_URL = NetworkProperties.nAddress+"/ads/todayAds";
	private static final String TAG = "ADSMAIN";
	private ExpandableListView mListView = null;
	private ExpandAdapter mAdapter = null;
	private JSONArray mData;
	SharedPreferences sp;
	String sellerId;

	/** Called when the activity is first created. */
	public void AdsAdd(MenuItem item) {
		//startActivity(new Intent(this, AddadsActivity.class));
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		initData();
		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		JSONObject item = mAdapter.getChild(groupPosition, childPosition);
		Intent intent = new Intent();
		
		try{
		intent.putExtra("adsId", item.getString("adsId"));
		intent.putExtra("title", item.getString("title"));
		intent.putExtra("description", item.getString("description"));
		intent.putExtra("distance", item.getString("distance"));
		intent.putExtra("attribute",item.getString("attribute"));
		
        intent.setClass(AdsMainListActivity.this, AdsListActivity.class);
		 startActivity(intent);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
				//getMenuInflater().inflate(R.menu.ads_manage, menu);
				return true;

	}

	private void initData() {
		AsyncHttpClient client = new AsyncHttpClient();
		 RequestParams params=new RequestParams();
		 params.add("sellerId", sellerId);
		 params.add("latitude", "53.0");
		 params.add("longtitude", "-6.0");
         client.get(SERVICE_URL,params ,new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				if(arg0==404)
					Toast.makeText(getApplicationContext(), "Requested Resource not found",
							Toast.LENGTH_LONG).show();
				else if(arg0==500)
					Toast.makeText(getApplicationContext(), "Something wrong at the server end",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), "Unexpected error",
							Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] Response) {
				
				String reponse=byteToString(Response);
				
				Toast.makeText(getApplicationContext(), reponse,
						Toast.LENGTH_LONG).show();
				
				tranferToList(reponse);
				
			}
			private String byteToString(byte[] response) {
				String a="";
				for(int i=0;i<response.length;i++)
					a+=(char)response[i];
				return a;
			}

         });
		
	}


	private void tranferToList(String response) {
		try {
			JSONArray json=new JSONArray(response);
			JSONArray jsonArray=(JSONArray) json;
			
			mData = jsonArray;

			Toast.makeText(this, "success",
					Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		mListView = new ExpandableListView(this);
		mListView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		setContentView(mListView);

		mListView.setGroupIndicator(getResources().getDrawable(
				R.drawable.expander_floder));
		mAdapter = new ExpandAdapter(this, mData);
		mListView.setAdapter(mAdapter);
		mListView
				.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
		mListView.setOnChildClickListener(this);
	}
	
}