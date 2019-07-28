package com.samsum.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.samsum.R;
import com.samsum.utils.Utilities;


public class GridViewAdapter extends BaseAdapter {

	private Context context;
	private int numberOfGrids;
	private LayoutInflater inflater = null;
	private int color;
	private int correctGrid;
	private int labelTimes;

	public GridViewAdapter(Context context, int numberOfGrids, int color, int correctGrid, int labelTimes) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.numberOfGrids = numberOfGrids;
		this.color = color;
		this.correctGrid = correctGrid;
		this.labelTimes = labelTimes;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return numberOfGrids * numberOfGrids;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder {
		ImageView img;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = new Holder();
		View rowView;

		rowView = inflater.inflate(R.layout.grid_view_row, null);
		holder.img = (ImageView) rowView.findViewById(R.id.img_view_grid_row);
		
		
		holder.img.setLayoutParams(new RelativeLayout.LayoutParams(Utilities.getDeviceWidth(context) / numberOfGrids, Utilities.getDeviceWidth(context) / numberOfGrids));
		
		if(position == correctGrid) {
			holder.img.setBackgroundColor(color);
			switch (numberOfGrids - 1) {
			case 1:
				holder.img.setAlpha(0.4f);
				break;
			case 2:
				if(labelTimes == 1) {
					holder.img.setAlpha(0.45f);	
				} else if(labelTimes == 2) {
					holder.img.setAlpha(0.50f);	
				} else if(labelTimes == 3) {
					holder.img.setAlpha(0.55f);	
				} else if(labelTimes == 4) {
					holder.img.setAlpha(0.60f);	
				}
						
				break;
			case 3:
				if(labelTimes == 1) {
					holder.img.setAlpha(0.62f);	
				} else if(labelTimes == 2) {
					holder.img.setAlpha(0.64f);	
				} else if(labelTimes == 3) {
					holder.img.setAlpha(0.66f);	
				} else if(labelTimes == 4) {
					holder.img.setAlpha(0.68f);	
				} else if(labelTimes == 5) {
					holder.img.setAlpha(0.70f);	
				} else if(labelTimes == 6) {
					holder.img.setAlpha(0.72f);	
				} else if(labelTimes == 7) {
					holder.img.setAlpha(0.74f);	
				} else {
					holder.img.setAlpha(0.75f);
				}
				break;
			case 4:
				if(labelTimes == 1) {
					holder.img.setAlpha(0.76f);	
				} else if(labelTimes == 2) {
					holder.img.setAlpha(0.78f);	
				} else if(labelTimes == 3) {
					holder.img.setAlpha(0.80f);	
				} else if(labelTimes == 4) {
					holder.img.setAlpha(0.82f);	
				} else if(labelTimes == 5) {
					holder.img.setAlpha(0.84f);	
				} else if(labelTimes == 6) {
					holder.img.setAlpha(0.86f);	
				} else if(labelTimes == 7) {
					holder.img.setAlpha(0.86f);	
				} else {
					holder.img.setAlpha(0.87f);
				}
				break;
			case 5:
				if(labelTimes == 5) {
					holder.img.setAlpha(0.90f);	
				} else if(labelTimes == 10) {
					holder.img.setAlpha(0.92f);	
				} else if(labelTimes == 15) {
					holder.img.setAlpha(0.94f);	
				} else if(labelTimes >= 20) {
					holder.img.setAlpha(0.96f);	
				} else {
					holder.img.setAlpha(0.88f);	
				}
				break;

			default:
				break;
			}
			
		} else {
			holder.img.setBackgroundColor(color);
		}

		return rowView;
	}

}
