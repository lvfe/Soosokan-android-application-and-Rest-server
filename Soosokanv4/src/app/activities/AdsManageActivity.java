package app.activities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import app.entities.AdsEntity;
import app.entities.NetworkProperties;

import com.example.soosokanv3.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class AdsManageActivity extends Activity implements
		OnChildClickListener {
	private static final String SERVICE_URL = NetworkProperties.nAddress+"/ads/bySeller";
	private ExpandableListView mListView = null;
	private ExpandAdapter mAdapter = null;
	private JSONArray mData;
	private List<AdsEntity> foodList = new ArrayList<AdsEntity>();
	List<AdsEntity> wearList = new ArrayList<AdsEntity>();
	private List<AdsEntity> dUseList = new ArrayList<AdsEntity>();
	List<AdsEntity> otherList = new ArrayList<AdsEntity>();
	SharedPreferences sp;
	String sellerId;

	/** Called when the activity is first created. */
	public void AdsAdd(MenuItem item) {
		startActivity(new Intent(this, AddadsActivity.class));
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = this.getSharedPreferences("userInfo", 0);
		sellerId = sp.getString("EMAIL", "");
//		mData.add(foodList);
//		mData.add(wearList);
//		mData.add(dUseList);
//		mData.add(otherList);
		initData();
		
	}
//	public static String getJsonContent(String path){
//        
//            
//			try {
//				URL url=new URL(SERVICE_URL);
//	            HttpURLConnection connection;
//				connection = (HttpURLConnection) url.openConnection();
//			
//            connection.setConnectTimeout(3000);
//            connection.setRequestMethod("GET");
//            connection.setDoInput(true);
//            int code=connection.getResponseCode();
//           
//            return changeInputString(connection.getInputStream());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//return "";
//    }
// 
//    private static String changeInputString(InputStream inputStream) {
//         
//        String jsonString="";
//        ByteArrayOutputStream outPutStream=new ByteArrayOutputStream();
//        byte[] data=new byte[1024];
//        int len=0;
//        try {
//            while((len=inputStream.read(data))!=-1){
//                outPutStream.write(data, 0, len);
//            }
//            jsonString=new String(outPutStream.toByteArray());
//         
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return jsonString;
//    }

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		JSONObject item = mAdapter.getChild(groupPosition, childPosition);
		Intent intent = new Intent();
		
//		AdsEntity ads=new AdsEntity(item.getString("adsId"), 
//				item.getString("sellerId"), item.getString("title"), 
//				item.getString("description"), time, distance, attribute)
		try{
		intent.putExtra("adsId", item.getString("adsId"));
		//intent.putExtra("sellerId", item.getString(sellerId));
		System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTest");
		intent.putExtra("title", item.getString("title"));
		intent.putExtra("description", item.getString("description"));
		//String time=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(ads.getTime());
		//intent.putExtra("time", item.getString("adsId"));
		intent.putExtra("distance", item.getString("distance"));
		intent.putExtra("attribute",item.getString("attribute"));
		
        intent.setClass(AdsManageActivity.this, AdsDetailActivity.class);
		 startActivity(intent);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.ads_manage, menu);
				return true;

	}

	private void initData() {
		AsyncHttpClient client = new AsyncHttpClient();
		 RequestParams params=new RequestParams();
		 params.add("sellerId", sellerId);
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
				
				String reponse=NetworkProperties.byteToString(Response);
				
				Toast.makeText(getApplicationContext(), reponse,
						Toast.LENGTH_LONG).show();
				
				tranferToList(reponse);
				
			}
			

		
		
         });
		
	}

//	private String[] getStringArray(int resId) {
//		return getResources().getStringArray(resId);
//	}
//	


	private void tranferToList(String response) {
		try {
			JSONArray json=new JSONArray(response);
			JSONArray jsonArray=(JSONArray) json;
			
			mData = jsonArray;
			
			
//			JSONArray foodlistA = jsonArray.getJSONArray(0);
//			JSONArray wearinglistA = jsonArray.getJSONArray(1);
//			JSONArray DUlistA = jsonArray.getJSONArray(2);
//			JSONArray olistA = jsonArray.getJSONArray(3);
//			
//			for(int j=0;j<foodlistA.length();j++){
//				AdsEntity ads=JSONTOAds((JSONObject)foodlistA.get(j));
//				foodList.add(ads);
//				//System.out.println(ads.toString());
//			}
//			for(int j=0;j<wearinglistA.length();j++){
//				AdsEntity ads=JSONTOAds((JSONObject)wearinglistA.get(j));
//				wearList.add(ads);
//			}
//			for(int j=0;j<DUlistA.length();j++){
//				AdsEntity ads=JSONTOAds((JSONObject)DUlistA.get(j));
//				dUseList.add(ads);
//			}
//			for(int j=0;j<olistA.length();j++){
//				AdsEntity ads=JSONTOAds((JSONObject)olistA.get(j));
//				otherList.add(ads);
//			}
					

			
			
			

//			   else Toast.makeText(this, "no such categoty",
//						Toast.LENGTH_LONG).show();
			   
//			}
			//mData 连续加四次数据分别配置到不同的group
//			mData.add(foodList);
//			mData.add(wearList);
//			mData.add(dUseList);
//			mData.add(otherList);
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
	
	
	private AdsEntity JSONTOAds(JSONObject myJsonObject) {
		AdsEntity ae=null;
		try {
		String adsId=myJsonObject.getString("_id");
		 String sellerId=myJsonObject.getString("sellerId");
		 String adsTitle = myJsonObject.getString("title");
		   String adsdes =  myJsonObject.getString("description");
		   String timeString;
		
			timeString = myJsonObject.getString("time");
		
		  
		   String distanceString =  myJsonObject.getString("distance");
		   int distance=Integer.valueOf(distanceString);
		   String attribute=myJsonObject.getString("attribute");
		   ae=new AdsEntity(adsId, sellerId, adsTitle,
				   adsdes, timeString, distance,attribute);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ae;
	}
}