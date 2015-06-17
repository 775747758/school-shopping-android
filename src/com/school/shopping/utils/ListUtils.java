package com.school.shopping.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;
import com.school.shopping.entity.Province;
import com.school.shopping.fragment.Fragment_Home;
import com.school.shopping.vo.GoodVo;

public class ListUtils {

	
	// 对数据进行大小排序、小-->大
		public static List<String> sortCity(List<String> list) {
			if (list!=null&&list.size() > 1) {
				Comparator<String> mapComprator = new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						String o1p=PinyinHelper.convertToPinyinString(o1,"",PinyinFormat.WITHOUT_TONE);
						String o2p=PinyinHelper.convertToPinyinString(o2, "",PinyinFormat.WITHOUT_TONE);
						if (o1p.compareToIgnoreCase(o2p)>0) {
							return 1;
						} else {
							return -1;
						}
					}

				};
				Collections.sort(list, mapComprator);
			} else {
				new Exception("排序没有取到数据");
			}
			return list;
		}

}
