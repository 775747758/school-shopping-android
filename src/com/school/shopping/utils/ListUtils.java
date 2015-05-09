package com.school.shopping.utils;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

import com.school.shopping.entity.Good;
import com.school.shopping.entity.Province;
import com.school.shopping.showgoods.Activity_ShowGoods;
import com.school.shopping.vo.GoodVo;

public class ListUtils {

	// 对数据进行大小排序、大-->小
	public static List<GoodVo> sortGood(List<GoodVo> list, final int type, final boolean isAESC) {
		if (list.size() > 1) {
			Comparator<GoodVo> mapComprator = new Comparator<GoodVo>() {

				@Override
				public int compare(GoodVo lhs, GoodVo rhs) {
					switch (type) {
					case Activity_ShowGoods.SORT_TYPE_DATE:
						if (isAESC) {
							if (Integer.valueOf(lhs.getId()) < Integer.valueOf(rhs.getId())) {
								return 1;
							} else {
								return -1;
							}
						} else {
							if (Integer.valueOf(lhs.getId()) > Integer.valueOf(rhs.getId())) {
								return 1;
							} else {
								return -1;
							}
						}
					case Activity_ShowGoods.SORT_TYPE_DISTANCE:
					case Activity_ShowGoods.SORT_TYPE_PRICE:
						if (isAESC) {
							if (Integer.valueOf(lhs.getPrice()) < Integer.valueOf(rhs.getPrice())) {
								return 1;
							} else {
								return -1;
							}
						} else {
							if (Integer.valueOf(lhs.getPrice()) > Integer.valueOf(rhs.getPrice())) {
								return 1;
							} else {
								return -1;
							}
						}
					}
					return 0;
				}

			};
			Collections.sort(list, mapComprator);
		} else {
			new Exception("排序没有取到数据");
		}
		return list;
	}

	// 对数据进行大小排序、小-->大
	public static List<Province> sortProvince(List<Province> list) {
		if (list!=null&&list.size() > 1) {
			Comparator<Province> mapComprator = new Comparator<Province>() {

				@Override
				public int compare(Province o1, Province o2) {
					String o1p=PinyinHelper.convertToPinyinString(o1.getProName(),"",PinyinFormat.WITHOUT_TONE);
					String o2p=PinyinHelper.convertToPinyinString(o2.getProName(), "",PinyinFormat.WITHOUT_TONE);
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
