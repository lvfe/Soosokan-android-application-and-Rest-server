package app.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soosokanfu.R;

public class ItemListAdapter extends BaseAdapter{
	

	private Context mContext;
    private LayoutInflater mInflater = null;
   // private String[]   mGroupStrings = {"Food","Wearing","Daliy Use","Others"};
    private JSONArray  mData = null;

    public ItemListAdapter(Context ctx, JSONArray list) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //mGroupStrings = mContext.getResources().getStringArray(R.array.groups);
        mData = list;
    }

    public void setData(JSONArray list) {
        mData = list;
    }

    

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    
    private class ViewHolder {
        TextView mName;
        TextView mPrice;
        ImageView mIcon;
        
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length();
	}

	@Override
	public JSONObject getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return mData.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_item, null);
        }
		
		ViewHolder holder = new ViewHolder();
		
		holder.mName = (TextView) convertView
                .findViewById(R.id.row_ItemName);
		holder.mPrice = (TextView) convertView
                .findViewById(R.id.row_ItemPrice);
		holder.mIcon = (ImageView) convertView.findViewById(R.id.itempic);
		convertView.setTag(holder);
		
		String price = "--";
		String name = "--";
		
		try {
			price = getItem(position).getString("price");
			name = getItem(position).getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			holder.mName.setText(name);
			holder.mPrice.setText(price);
		return convertView;
	}

}
