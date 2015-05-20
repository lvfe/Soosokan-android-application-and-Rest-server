package app.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import app.entities.NetworkProperties;
import app.entities.SysApplication;
import app.location.MyLocationManager;

import com.example.soosokanv3.R;

public class RegisterActivity extends Activity {

	private static final String SERVICE_URL = NetworkProperties.nAddress+"/seller/add";
	public final static int REFRESH_LOCATION = 0x0100;
	
	public static final String TAG = "REGISTER";
	
	private Handler mHandler;
	private MyLocationManager myLocationManager;
//
	private long curTimeGPS;
	private Location location;
	private String address;
	private long curTimeNetwork;
	String longtitude;
	String latitude ;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		setContentView(R.layout.activity_register);
		initLogic();
		getLocationByGPS();
	}

	private boolean isValidEmail(String mail) {
		Pattern pattern = Pattern
				.compile("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
		Matcher matcher = pattern.matcher(mail);
		return matcher.matches();
	}

	public void Register(View vw) {

		EditText edFirstName = (EditText) findViewById(R.id.Firstname_editText);
		EditText edTelphone = (EditText) findViewById(R.id.Phoneno_editText);
		EditText edEmail = (EditText) findViewById(R.id.Email_editText);
		EditText edAddress = (EditText)findViewById(R.id.Selleraddress_editText);
		EditText edPassword_1 = (EditText) findViewById(R.id.Password_1_editText);
		EditText edPassword_2 = (EditText) findViewById(R.id.Password_2_editText);

		String firstName = edFirstName.getText().toString();
		String telphone = edTelphone.getText().toString();
		String email = edEmail.getText().toString();
		String address = edAddress.getText().toString();
		String password_1 = edPassword_1.getText().toString();
		String password_2 = edPassword_2.getText().toString();

		if (firstName.equals("") || telphone.equals("") || email.equals("")
				|| address.equals("") || password_1.equals("") || password_2.equals("")) {
			Toast.makeText(this, "Please enter in all required fields.",
					Toast.LENGTH_LONG).show();
			return;
		} else if (isValidEmail(email) == false) {
			Toast.makeText(this, "Email address is not Valid.",
					Toast.LENGTH_LONG).show();
			return;

		} else if (!password_1.equals(password_2)) {
			Toast.makeText(this, "Password does not match.", Toast.LENGTH_LONG)
					.show();
			return;
		}

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"Posting data...");

		Toast.makeText(this, longtitude + latitude,
				Toast.LENGTH_LONG).show();
		
		wst.addNameValuePair("name", firstName);
		wst.addNameValuePair("telphone", telphone);
		wst.addNameValuePair("userName", email);
		
		wst.addNameValuePair("password", password_1);
		wst.addNameValuePair("email", email);
		
		String expiredate = "00-00-0000";
		 longtitude =  Double.toString(location.getLongitude());
		 latitude = Double.toString(location.getLatitude());
		
		
		wst.addNameValuePair("longtitude", longtitude);
		wst.addNameValuePair("latitude", latitude);
		wst.addNameValuePair("expiredate", expiredate);
		wst.addNameValuePair("description", address);

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponseLocal(String response) {
//		try {
//			Toast.makeText(this, response, Toast.LENGTH_LONG).show();
//
//		} catch (Exception e) {
//			Log.e(TAG, e.getLocalizedMessage(), e);
//		}
		if (response.endsWith("true")){
			Toast.makeText(this, response, Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.setClass(RegisterActivity.this, LoginActivity.class);
			startActivity(intent);
			RegisterActivity.this.finish();
		}
		
	}
	
	public void getLocationByGPS()
	{
		
		location = myLocationManager.getLocationByGps();
		//String address = null;
		if (location != null)
		{
			address = myLocationManager.queryAddressByGoogle(location.getLatitude(), location.getLongitude());
		}
		
		
		//showNewGPSLocation(location, address);
	}
	
	public void getLocationByNetwork()
	{
		
		 location = myLocationManager.getLocationByNetwork();
		//String address = null;
		if (location != null)
		{
			address = myLocationManager.queryAddressByGoogle(location.getLatitude(), location.getLongitude());
		}
		
	
		//showNewNetworkLocation(location, address);
	}
	
	
	 public void initLogic()
	    {
	    	mHandler = new Handler()
	    	{

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					switch(msg.what)
					{
					case REFRESH_LOCATION:
						Location location = (Location) msg.obj;
						
						Toast.makeText(RegisterActivity.this,
	                            "onLocationChanged provider = ..." + location.getProvider(),
	                            Toast.LENGTH_SHORT).show();
						
						
						if (location.getProvider().equals(LocationManager.GPS_PROVIDER))
						{
							long inteval = System.currentTimeMillis() - curTimeGPS;
							curTimeGPS = System.currentTimeMillis();
							
							String address = null;
							if (location != null)
							{
								address = myLocationManager.queryAddressByGoogle(location.getLatitude(), location.getLongitude());
							}		
							//showChangeGPSLocation(location, address, inteval);
						}else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
						{
							long inteval = System.currentTimeMillis() - curTimeNetwork;
							curTimeNetwork = System.currentTimeMillis();
							
							String address = null;
							if (location != null)
							{
								address = myLocationManager.queryAddressByGoogle(location.getLatitude(), location.getLongitude());
							}		
							//showChangeNetworkLocation(location, address, inteval);
						}
						
						break;
						default:
							break;
					}
				}
	    		
	    	};
	    	
	    	myLocationManager = new MyLocationManager(this, mHandler);
	    }

	private class WebServiceTask extends AbstractWebServiceTask {

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {
			super(taskType, mContext, processMessage);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void hideKeyboard() {
			InputMethodManager inputManager = (InputMethodManager) RegisterActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(RegisterActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

		}

		@Override
		public void handleResponse(String response) {
			// TODO Auto-generated method stub
			handleResponseLocal(response);

		}
	}
}
