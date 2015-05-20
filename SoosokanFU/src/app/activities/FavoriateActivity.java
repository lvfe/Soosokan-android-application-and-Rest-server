package app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import app.Entity.SysApplication;

import com.example.soosokanfu.R;

public class FavoriateActivity extends Activity {
	private static final String TAG = "FAVORITE";

	public FavoriateActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.addActivity(this, TAG);
        setContentView(R.layout.favourite);
        Intent intent=new Intent(FavoriateActivity.this,AdsMainListActivity.class);
        startActivity(intent);
    }
}
