package app.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.entities.NetworkProperties;
import app.entities.SysApplication;
import app.preview.SaveBitMap;

import com.example.soosokanv3.R;

public class AddItemActivity extends Activity {

	public static final String TAG = "ITEMADD";
	private static final String SERVICE_URL = NetworkProperties.nAddress
			+ "/item/add";
	private static final String SEARCH_URL = NetworkProperties.nAddress
			+ "/search";

	private EditText itemprice, itemkeyword, itemname, discountno;
	private String itempriceString, itemkeywordString, itemnameString,
			discountnoString;
	private CheckBox checkdiscount;
	private Button add_item_btn, add_photo;
	private ImageView take_photo;
	//Bitmap pict = SaveBitMap.getImageFromSDCard("1");
	// spinner
	private TextView view;
	private ImageButton item_pic;
	Bitmap bm;
	SharedPreferences sp;
	String sellerId;


	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		setContentView(R.layout.activity_item_add);
		sp = this.getSharedPreferences("userInfo", 0);
		sellerId = sp.getString("EMAIL", "");

		itemprice = (EditText) findViewById(R.id.itemprice);
		itemkeyword = (EditText) findViewById(R.id.itemkeyword);
		itemname = (EditText) findViewById(R.id.itemname);
		discountno = (EditText) findViewById(R.id.discountno);

		add_item_btn = (Button) findViewById(R.id.add_item_btn);
		add_photo = (Button) findViewById(R.id.add_photo);
		

		// take_photo = (ImageView) findViewById(R.id.take_photo);

		// // 获取XML中定义的数组
		// String[] ls = getResources().getStringArray(R.array.range);
		//
		// // 把数组导入到ArrayList中
		// for (int i = 0; i < ls.length; i++) {
		// list.add(ls[i]);
		// }
//		item_pic.setOnClickListener(mylistener);
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(AddItemActivity.this,
//						CameraActivity.class);
//				startActivity(intent);
//			}
//
//		});

		 bm = SaveBitMap.getImageFromSDCard("1");
//		if (bm.equals(null)) {
//
//		} else {
//			item_pic.setImageBitmap(bm);
//		}
		// take_photo.setImageBitmap(bm);

	}

	public void item_add_btn(View vw) {

		itempriceString = itemprice.getText().toString();
		itemkeywordString = itemkeyword.getText().toString();
		itemnameString = itemname.getText().toString();
		discountnoString = discountno.getText().toString();

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"Posting data...");

		wst.addNameValuePair("Itemprice", itempriceString);
		wst.addNameValuePair("Itemkeyword", "soosokan");
		wst.addNameValuePair("Itemname", itemnameString);
		wst.addNameValuePair("Discountno", discountnoString);
		wst.addNameValuePair("sellerId", sellerId);
		wst.addNameValuePair("Picture", SaveBitMap.FileToString("1"));
		
		

		// Pic2Base64.GenerateImage(imgStr, filePath)

		wst.execute(new String[] { SERVICE_URL });

	}

	public void handleResponseLocal(String response) {
		if (!response.equals("ok")) {
			Toast.makeText(this, "Fail to save, Please save again.",
					Toast.LENGTH_LONG).show();

		}
		else{
			Toast.makeText(this, "Save successfully.",
					Toast.LENGTH_LONG).show();
			
			SaveBitMap.deleteFile("1");
			AddItemActivity.this.finish();
			
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
			InputMethodManager inputManager = (InputMethodManager) AddItemActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(AddItemActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

		}

		@Override
		public void handleResponse(String response) {
			// TODO Auto-generated method stub
			handleResponseLocal(response);
		}

	}
	
		public void item_pic_button(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.item_pic:
				Intent intent = new Intent(AddItemActivity.this,
						CameraActivity.class);
				startActivity(intent);
				break;
			
			}
			
		}
	}
	


