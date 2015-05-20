package app.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import app.Entity.ItemEntity;
import app.Entity.NetworkProperties;
import app.Entity.SysApplication;

import com.example.soosokanfu.R;

public class SearchResultActivity extends Activity {
	private static final String SERVICE_URL = NetworkProperties.nAddress+"/search";
	private static final String TAG = "SEARCHRESULT";
	private List<ItemEntity> list=new ArrayList<ItemEntity>();
	
	
	 public SearchResultActivity() {
		 ItemEntity item=null;
//				 new Item(String itemId, String sellerId, String name,
//					String keyword, float price, Timestamp time, int discount, String filePath);
//		list.add(item);
	}

	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        SysApplication.addActivity(this, TAG);
	        setContentView(R.layout.activity_shoplist);
	    }
	 
	 public void postData(View vw) {

			WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
					"Posting data...");

//			wst.addNameValuePair("email", email);
//			wst.addNameValuePair("password", password);
//
//			// the passed String is the URL we will POST to
			wst.execute(new String[] { SERVICE_URL });
			

		}
	 private class WebServiceTask extends AbstractWebServiceTask {

			public WebServiceTask(int taskType, Context mContext,
					String processMessage) {
				super(taskType, mContext, processMessage);
				// TODO Auto-generated constructor stub
			}

			@Override
			protected void hideKeyboard() {
				// TODO Auto-generated method stub
				InputMethodManager inputManager = (InputMethodManager) SearchResultActivity.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(SearchResultActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

			}

			@Override
			public void handleResponse(String response) {
				// TODO Auto-generated method stub
				handleResponseLocal(response);
			}

		}
	 public void handleResponseLocal(String response) {
			try {
				if(!response.equals(null)) {
					
//					JSONArray arr=(JSONArray) JSON.parse(response);
//					   JSONObject o=(JSONObject) arr.get(0);
//					   System.out.println(o.get("EMAIL"));
					   
					 JSONObject myJsonObject = new JSONObject(response);
					   //获取对应的值
					   String email = myJsonObject.getString("email");
					   String name =  myJsonObject.getString("name");
					   String address =  myJsonObject.getString("description");
					   
					   String password = myJsonObject.getString("password");
					   
					   if(password.equals("wrong")){
						   Toast.makeText(this, "Wrong Password! Please Re-enter!",
									Toast.LENGTH_LONG).show();
						   
						   
					   }else{
						  // Editor editor = sp.edit();
//							editor.putString("EMAIL", email);
//							editor.putString("NAME", name);
//							editor.putString("ADDRESS", address);
//							
//							editor.commit();
							Toast.makeText(this, response, Toast.LENGTH_LONG).show();
//							Intent intent = new Intent();
//							intent.setClass(SearchResultActivity.this, SellerMainActivity.class);
//							startActivity(intent);
							SearchResultActivity.this.finish();
					   }
					   
					   
					  

					
					
				} else {
					Toast.makeText(SearchResultActivity.this, response, Toast.LENGTH_LONG).show();
				}

			} catch (Exception e) {
//				Log.e(TAG, e.getLocalizedMessage(), e);
			}

		}

}
