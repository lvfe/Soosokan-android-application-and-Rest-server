package app.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import app.Entity.NetworkProperties;
import app.Entity.SysApplication;

import com.example.soosokanfu.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ShopListActivity extends Activity{
	private static final String TAG = "SHOPLIST";
//change url
	private static final String SERVICE_URL = NetworkProperties.nAddress+"/item/search";



	private ListView mListView ;
	private JSONArray mData;
	private ShopListAdapter mAdapter;
	private String keyword;
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		setContentView(R.layout.shoplist_main);
		
		mListView = (ListView)findViewById(R.id.shoplist_list);
		
		
	    
	    initData();
	}
	
	private void initData() {
		// TODO Auto-generated method stub
			AsyncHttpClient client = new AsyncHttpClient();
			 RequestParams params=new RequestParams();
			 Intent intent = getIntent(); //用于激活它的意图对象
			 String keyword = intent.getStringExtra("searchKeyword");
			 double longtitude = intent.getDoubleExtra("longtitude", 0);
			 double latitude = intent.getDoubleExtra("latitude", 0);
			 
			 params.add("keyword", keyword);
			 params.add("longtitude", String.valueOf(longtitude));
			 params.add("latitude", String.valueOf(latitude));
			 Toast.makeText(getApplicationContext(), String.valueOf(longtitude),
						Toast.LENGTH_LONG).show();

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
			
			final JSONArray json=new JSONArray(response);
			
			mData = json;
			mListView = new ListView(this);

			mListView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));
			setContentView(mListView);
			 mAdapter = new ShopListAdapter(this, mData);
			mListView.setAdapter(mAdapter);
			mListView
			.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
			
			mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ArrayList<String> result = new ArrayList<String>();

					JSONObject obj = mAdapter.getItem(position);

					JSONArray array;
					Intent intent = new Intent();
					try {
						array = obj.getJSONArray("result");
						for (int i = 0; i < array.length(); i++) {
							result.add(array.getString(i));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("result size in shoplist:"
							+ result.size());
					intent.putStringArrayListExtra("result", result);
					intent.setClass(ShopListActivity.this, ItemofOneShopListActivity.class);
					startActivity(intent);

				}
				
				
			});
			
			Toast.makeText(this, "success",
					Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		int item_id = item.getItemId();
		return true;
	}
}
