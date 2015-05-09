package com.school.shopping.adapter;

import java.util.List;

import com.school.shopping.entity.Good;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.FriendHolder;
import com.school.shopping.net.FriendsProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.vo.FriendVo;

public class Adapter_Friends extends DefaultAdapter<FriendVo>{

	private List<FriendVo> friends;
	
	private FriendsProtocal protocal;
	

	public void setProtocal(FriendsProtocal protocal) {
		this.protocal = protocal;
	}

	public Adapter_Friends(List<FriendVo> data) {
		super(data);
		this.friends=data;
	}

	@Override
	protected void itemClick(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BaseHolder getHolder() {
		return new FriendHolder();
	}

	@Override
	protected List onLoadMore() {
		if(protocal!=null){
			if(friends==null){
				LogUtils.i("旧数据为空，所以加载0--20");
				return protocal.load(0, 20,false);
			}
			else{
				LogUtils.i("加载"+friends.size()+"---"+(friends.size()+20));
				return protocal.load(friends.size(), friends.size()+20,false);
			}
		}
		else{
			LogUtils.i("AdapterFriend:protocal:为空");
			return null;
		}
		
	}
	

}
