package app.activities;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import app.entities.NetworkProperties;

import com.example.soosokanv3.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ItemManageActivity extends Activity {
	private static final String SERVICE_URL = NetworkProperties.nAddress
			+ "/item/bySeller";

	private ListView mListView;
	private JSONArray mData;
	private String itemid;
	SharedPreferences sp;
	private ItemListAdapter mAdapter;
	String sellerId;
	ArrayList<HashMap<String, Object>> ItemList_item;

	public void ItemAdd(MenuItem item) {
		startActivity(new Intent(this, AddItemActivity.class));
	}

	public void ItemMod(MenuItem item) {
		startActivity(new Intent(this, ModItemActivity.class));
	}

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		sp = this.getSharedPreferences("userInfo", 0);
		sellerId = sp.getString("EMAIL", "");
		ItemList_item = new ArrayList<HashMap<String, Object>>();

		initData();

		setContentView(R.layout.activity_item_manage);

	}

	private void initData() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("sellerId", sellerId);
		client.get(SERVICE_URL, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				if (arg0 == 404)
					Toast.makeText(getApplicationContext(),
							"Requested Resource not found", Toast.LENGTH_LONG)
							.show();
				else if (arg0 == 500)
					Toast.makeText(getApplicationContext(),
							"Something wrong at the server end",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), "Unexpected error",
							Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] Response) {

				String reponse = byteToString(Response);

				Toast.makeText(getApplicationContext(), reponse,
						Toast.LENGTH_LONG).show();

				tranferToList(reponse);

			}

			private String byteToString(byte[] response) {
				String a = "";
				for (int i = 0; i < response.length; i++)
					a += (char) response[i];
				return a;
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_manage, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();

		return true;
	}

	private void tranferToList(String response) {
		try {
			// JSONArray jsonArray=new JSONArray(response);

			JSONArray json = new JSONArray(response);
			System.out.println("json" + json.getJSONObject(0).get("name"));

			// JSONArray jsonArray=(JSONArray) json;

			mData = json;
			mListView = new ListView(this);

			mListView.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			setContentView(mListView);
			mAdapter = new ItemListAdapter(this, mData);
			mListView.setAdapter(mAdapter);
			mListView
					.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					JSONObject item = mAdapter.getItem(position);
					try {
						itemid = item.getString("itemId");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent = new Intent();
					intent.putExtra("itemId", itemid);

					System.out.println("Item manage:"+ itemid);
					intent.setClass(ItemManageActivity.this,
							ItemDetailActivity.class);
					startActivity(intent);

				}

			});
			Toast.makeText(getApplicationContext(), itemid,
					Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
