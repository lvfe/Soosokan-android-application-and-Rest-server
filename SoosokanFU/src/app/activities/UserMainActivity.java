package app.activities;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;
import app.Entity.NetworkProperties;
import app.Entity.SysApplication;
import app.location.MyLocationManager;

import com.example.soosokanfu.R;
import com.facebook.AppEventsLogger;

public class UserMainActivity extends TabActivity implements
		OnCheckedChangeListener {

	// private static final String SERVICE_URL =
	// NetworkProperties.nAddress+"/search";
	public final static int REFRESH_LOCATION = 0x0100;
	public static final String TAGFU = "AndroidUCActivity";

	private Handler mHandler;

	private MyLocationManager myLocationManager;
	//
	private long curTimeGPS;
	private Location location;
	private String address;
	private long curTimeNetwork;
	String longtitude;
	String latitude;

	private static final String TAG = "USERMAIN";
	private Intent aIntent;
	private Intent fIntent;
	// private Intent mIntent;
	private Intent accountIntent;
	private RadioButton radio_home;
	private RadioButton radio_service;
	private RadioButton radio_account;
	private TabHost mTabHost;
	private AutoCompleteTextView search_autocompletetextview;
	private ImageButton IBtn;
	private Editable s;
	private String searchString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		setContentView(R.layout.activity_usermain);
		search_autocompletetextview = (AutoCompleteTextView) findViewById(R.id.search_autocompletetextview);
		Editable s = search_autocompletetextview.getText();

		initLogic();
		getLocationByNetwork();
		getLocationByGPS();

		IBtn = (ImageButton) findViewById(R.id.search_button);
		IBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editable s = search_autocompletetextview.getText();
				// search_autocompletetextview.setText(s+"hahahhahah");
				searchString = s.toString();

				Intent intent = new Intent();
				intent.setClass(UserMainActivity.this, ShopListActivity.class);
				searchString = s.toString();
				intent.putExtra("searchKeyword", searchString);

				// longtitude = Double.toString(location.getLongitude());
				// latitude = Double.toString(location.getLatitude());

				longtitude = Double.toString(53.0);
				latitude = Double.toString(-6.0);

				intent.putExtra("longtitude", longtitude);
				intent.putExtra("latitude", latitude);

				startActivity(intent);
			}

		});

		this.aIntent = new Intent(this, AdsMainListActivity.class);
		this.fIntent = new Intent(this, FavoriateActivity.class);
		this.accountIntent = new Intent(this, AccountActivity.class);

		radio_home = (RadioButton) findViewById(R.id.radio_home);
		radio_service = (RadioButton) findViewById(R.id.radio_service);
		radio_account = (RadioButton) findViewById(R.id.radio_account);
		radio_home.setChecked(true);

		radio_home
				.setOnCheckedChangeListener((android.widget.CompoundButton.OnCheckedChangeListener) this);
		radio_service
				.setOnCheckedChangeListener((android.widget.CompoundButton.OnCheckedChangeListener) this);
		radio_account
				.setOnCheckedChangeListener((android.widget.CompoundButton.OnCheckedChangeListener) this);

		setupIntent();
	}

	public void getLocationByGPS() {

		location = myLocationManager.getLocationByGps();
		// String address = null;
		if (location != null) {
			address = myLocationManager.queryAddressByGoogle(
					location.getLatitude(), location.getLongitude());
		}

		// showNewGPSLocation(location, address);
	}

	public void getLocationByNetwork() {

		location = myLocationManager.getLocationByNetwork();
		// String address = null;
		if (location != null) {
			address = myLocationManager.queryAddressByGoogle(
					location.getLatitude(), location.getLongitude());
		}

		// showNewNetworkLocation(location, address);
	}

	public void initLogic() {
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case REFRESH_LOCATION:
					Location location = (Location) msg.obj;

					Toast.makeText(
							UserMainActivity.this,
							"onLocationChanged provider = ..."
									+ location.getProvider(),
							Toast.LENGTH_SHORT).show();

					if (location.getProvider().equals(
							LocationManager.GPS_PROVIDER)) {
						long inteval = System.currentTimeMillis() - curTimeGPS;
						curTimeGPS = System.currentTimeMillis();

						String address = null;
						if (location != null) {
							address = myLocationManager.queryAddressByGoogle(
									location.getLatitude(),
									location.getLongitude());
						}
						// showChangeGPSLocation(location, address, inteval);
					} else if (location.getProvider().equals(
							LocationManager.NETWORK_PROVIDER)) {
						long inteval = System.currentTimeMillis()
								- curTimeNetwork;
						curTimeNetwork = System.currentTimeMillis();

						String address = null;
						if (location != null) {
							address = myLocationManager.queryAddressByGoogle(
									location.getLatitude(),
									location.getLongitude());
						}
						// showChangeNetworkLocation(location, address,
						// inteval);
					}

					break;
				default:
					break;
				}
			}

		};

		myLocationManager = new MyLocationManager(this, mHandler);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_home:
				this.mTabHost.setCurrentTabByTag("Radio_Home");
				break;
			case R.id.radio_service:
				this.mTabHost.setCurrentTabByTag("Radio_Service");
				break;
			case R.id.radio_account:
				this.mTabHost.setCurrentTabByTag("Radio_Account");
				break;
			}
		}
	}

	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("Radio_Home", R.string.main_ads,
				R.drawable.tab_selector_home, this.aIntent));

		localTabHost.addTab(buildTabSpec("Radio_Service",
				R.string.main_service, R.drawable.tab_selector_favourite,
				this.fIntent));

		localTabHost.addTab(buildTabSpec("Radio_Account",
				R.string.main_account, R.drawable.tab_selector_account,
				this.accountIntent));
	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	public void search_btn(View vw) {

		// WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
		// this,
		// "Posting data...");
		//
		// wst.addNameValuePair("searchKeyword", searchString);
		// wst.addNameValuePair("longtitude", longtitude);
		// wst.addNameValuePair("latitude", latitude);
		//
		// // the passed String is the URL we will POST to
		// wst.execute(new String[] { SERVICE_URL });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_user_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// public void postData(View vw) {
	//
	//
	// }

	private class WebServiceTask extends AbstractWebServiceTask {

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {
			super(taskType, mContext, processMessage);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void hideKeyboard() {
			// TODO Auto-generated method stub
			InputMethodManager inputManager = (InputMethodManager) UserMainActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(UserMainActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

		}

		@Override
		public void handleResponse(String response) {
			// TODO Auto-generated method stub
			handleResponseLocal(response);

			// List<Item> items ;
			//
			//
			// Intent intent = new Intent();
			// //第一参数取的是这个应用程序的Context，生命周期是整个应用
			// //第二个参数是要跳转的页面的全路径
			// intent.setClassName( getApplicationContext(), TAGFU );
			// //Bundle类用作携带数据，它类似于Map，用于存放key-value名值对形式的值
			// Bundle b = new Bundle();
			// //b.putStringArrayList(key, value);
			// b.putStringArrayList("ItemList", items);
			// //此处使用putExtras，接受方就响应的使用getExtra
			// intent.putExtras( b );
			// startActivity(intent);

		}

	}

	public void handleResponseLocal(String response) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Logs 'app deactivate' App Event.
		AppEventsLogger.deactivateApp(this);
	}
}
