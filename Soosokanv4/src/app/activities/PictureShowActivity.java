package app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import app.entities.SysApplication;
import app.preview.SaveBitMap;

import com.example.soosokanv3.R;

public class PictureShowActivity extends Activity {

	private Button sure, cancel;
	private ImageView pic;

	public static final String TAG = "PICSHOW";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.addActivity(this, TAG);
		setContentView(R.layout.activity_picshow);

		sure = (Button) findViewById(R.id.photoupload_sure);
		cancel = (Button) findViewById(R.id.photoupload_cancel);
		pic = (ImageView) findViewById(R.id.photo_display_single);

		Bitmap bm = SaveBitMap.getImageFromSDCard("1");
		pic.setImageBitmap(bm);
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(PictureShowActivity.this, SellerMainActivity.class);
//				startActivity(intent);
				
				SysApplication.close(CameraActivity.TAG);
				PictureShowActivity.this.finish();
			}
		});
		
		
	}

}
