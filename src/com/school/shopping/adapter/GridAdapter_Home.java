package com.school.shopping.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.utils.UIUtils;

public class GridAdapter_Home extends BaseAdapter{
	
		String[] newLevels={"一成新","二成新","三成新","四成新","五成新","六成新","七成新","八成新","九成新","全新"};

		private  List<Good> data = new ArrayList<Good>();
		public GridAdapter_Home(List<Good> data) {
			this.data = data;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			convertView = UIUtils.inflate(R.layout.cell_home);
			ImageView iv_good=(ImageView) convertView.findViewById(R.id.iv_good);
			ImageView iv_isAdjust=(ImageView) convertView.findViewById(R.id.iv_isAdjust);
			CustomShapeImageView iv_portrait=(CustomShapeImageView) convertView.findViewById(R.id.iv_portrait);
			TextView tv_goodName=(TextView) convertView.findViewById(R.id.tv_goodName);
			TextView tv_newLevel=(TextView) convertView.findViewById(R.id.tv_newLevel);
			TextView tv_distance=(TextView) convertView.findViewById(R.id.tv_distance);
			TextView tv_price=(TextView) convertView.findViewById(R.id.tv_price);
			
			if(data.get(position).getIsAdjust()==1)
			{
				iv_isAdjust.setImageDrawable(UIUtils.getDrawable(R.drawable.isadjust));
			}
			else
			{
				iv_isAdjust.setImageDrawable(UIUtils.getDrawable(R.drawable.noadjust));
			}
			tv_goodName.setText(data.get(position).getGoodName());
			tv_goodName.setText(newLevels[data.get(position).getNewLevel()-1]);
			tv_distance.setText("100m");
			tv_price.setText("￥"+data.get(position).getPrice());
			
			
			return convertView;
		}

}
