package com.school.shopping.view;

import android.content.Context;
import android.widget.GridView;
import android.util.AttributeSet;

/**
 * Created by IntelliJ IDEA. User: zhUser Date: 13-1-24 Time: ����6:53
 */
public class NonScrollGridView extends GridView {
	public NonScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
