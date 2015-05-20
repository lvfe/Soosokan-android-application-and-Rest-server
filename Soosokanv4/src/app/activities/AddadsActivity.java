package app.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.entities.NetworkProperties;

import com.example.soosokanv3.R;

public class AddadsActivity extends Activity implements OnItemSelectedListener{

	private static final String SERVICE_URL = NetworkProperties.nAddress
			+ "/ads/add";

	private EditText edAdstitle, edAdsdes;
	private String Adstitle, Adsdes;
	SharedPreferences sp;
	String sellerId;

	// spinner

	TextView spinnerview1, spinnerview2;

	Spinner spinner1, spinner2;
	private String[] state = { "500", "1000", "2000" };
	private String[] type = { "Discount", "Voucher", "New Product", "Other" };
	
	OnItemSelectedListener l1,l2;

	private ArrayAdapter<String> adapter_state, adapter_type;

	private String selState;

	private String selType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ads_add);
		sp = this.getSharedPreferences("userInfo", 0);
		sellerId = sp.getString("EMAIL", "");

		edAdstitle = (EditText) findViewById(R.id.adstitle);
		edAdsdes = (EditText) findViewById(R.id.adsdes);

		spinnerview1 = (TextView) findViewById(R.id.spinnerText1);
		spinnerview2 = (TextView) findViewById(R.id.spinnerText2);
		spinner1 = (Spinner) findViewById(R.id.ads_range);
		spinner2 = (Spinner) findViewById(R.id.ads_type);

		 adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, state);
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter_state);
		spinner1.setOnItemSelectedListener(this);
        

	    adapter_type = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type);
		adapter_type
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter_type);
		spinner2.setOnItemSelectedListener(this);

		
		 l1 = new OnItemSelectedListener(){
			
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,
					long id) {

				spinner1.setSelection(position);
				String selState = (String) spinner1.getSelectedItem();
				

				// Showing selected spinner item
				spinnerview1.setText("Selected Range (M):" + selState);
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
			};
			
			
			
			l2 = new OnItemSelectedListener(){
				
				@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,
					long id) {
		 
			spinner2.setSelection(position);
			String selType = (String) spinner2.getSelectedItem();
			
			
			spinnerview2.setText("Belongs to Type:" + selType);
			}
			public void onNothingSelected(AdapterView<?> arg0) {

			}
			};
	}
	
	



	public void ads_add_btn(View vw) {

		Adstitle = edAdstitle.getText().toString();
		Adsdes = edAdsdes.getText().toString();

		if (Adstitle.equals("") || Adsdes.equals("")) {
			Toast.makeText(this, "Please enter in all required fields.",
					Toast.LENGTH_LONG).show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"Posting data...");
		wst.addNameValuePair("distance",selState );
		wst.addNameValuePair("adsType", selType);
		
		wst.addNameValuePair("sellerID", sellerId);
		wst.addNameValuePair("AdsTitle", Adstitle);
		wst.addNameValuePair("dsDes", Adsdes);
		

		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponseLocal(String response) {

		if (!response.equals(null)) {

			// JSONArray arr=(JSONArray) JSON.parse(response);
			// JSONObject o=(JSONObject) arr.get(0);
			// System.out.println(o.get("EMAIL"));

			JSONObject myJsonObject;
			try {
				myJsonObject = new JSONObject(response);

				String adsTitle = myJsonObject.getString("AdsTitle");
				String adsdes = myJsonObject.getString("Adsdes");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
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
			InputMethodManager inputManager = (InputMethodManager) AddadsActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(AddadsActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

		}

		@Override
		public void handleResponse(String response) {
			// TODO Auto-generated method stub
			handleResponseLocal(response);
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "aaaaaaaaaaaa",
				Toast.LENGTH_LONG).show();
   switch(view.getId()){
   case R.id.ads_range:
	   spinner1.setSelection(position);
		 selState = (String) spinner1.getSelectedItem();
		

		// Showing selected spinner item
		spinnerview1.setText("Selected Range (M):" + selState);
		 break;
		 
	  case R.id.ads_type:
		  spinner2.setSelection(position);
			 selType = (String) spinner2.getSelectedItem();
			
			
			spinnerview2.setText("Belongs to Type:" + selType);
			break;
			default:
				break;
			}
   
		
}





	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}


	

}
