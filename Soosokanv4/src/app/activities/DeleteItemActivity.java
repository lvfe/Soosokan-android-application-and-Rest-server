package app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import app.entities.NetworkProperties;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DeleteItemActivity extends Activity {
	private String ItemId;
	private static final String SERVICE_URL = NetworkProperties.nAddress+"/item/delete";
	private SharedPreferences sp;

	public DeleteItemActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = this.getIntent();
		ItemId= intent.getStringExtra("itemId");
		Toast.makeText(getApplicationContext(),
				ItemId, Toast.LENGTH_LONG)
				.show();
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		params.add("itemId", ItemId);
		params.add("test", "test");
		client.get(SERVICE_URL, params, new AsyncHttpResponseHandler(){
//		client.delete(SERVICE_URL, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, org.apache.http.Header[] arg1,
					byte[] arg2, Throwable arg3) {
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
			public void onSuccess(int arg0, org.apache.http.Header[] arg1,
					byte[] arg2) {
				String reponse = NetworkProperties.byteToString(arg2);

				Toast.makeText(getApplicationContext(), "delete success!",
						Toast.LENGTH_LONG).show();
				Intent intent=new Intent(DeleteItemActivity.this, ItemManageActivity.class);
				startActivity(intent);

			}
		});
		
	}
}
