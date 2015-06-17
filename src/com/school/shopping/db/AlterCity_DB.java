package com.school.shopping.db;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.school.shopping.DatabaseHelper;
import com.school.shopping.entity.Province;
import com.school.shopping.utils.UIUtils;

public class AlterCity_DB {

	private static DatabaseHelper dbHelper;
	private static SQLiteDatabase db;

	public static List<Province> getProvince() {
		List<Province> list=new ArrayList<Province>();
		// 初始化数据库
		dbHelper = new DatabaseHelper(UIUtils.getContext(), "city.db");
		db = dbHelper.getReadableDatabase();
		Cursor cursorTemp = db.rawQuery("select id, proName from province ",null);
		while (cursorTemp.moveToNext()) {
			Province province=new Province();
			String proName = cursorTemp.getString(cursorTemp.getColumnIndex("proName"));
			int id = cursorTemp.getInt(cursorTemp.getColumnIndex("id"));
			province.setId(id);
			province.setProName(proName);
			list.add(province);
		}
		cursorTemp.close();
		db.close();
		dbHelper.close();
		return list;
	}
	
	public static List<String> getCity(int proId) {
		List<String> list=new ArrayList<String>();
		// 初始化数据库
		dbHelper = new DatabaseHelper(UIUtils.getContext(), "city.db");
		db = dbHelper.getReadableDatabase();
		Cursor cursorTemp = db.rawQuery("select cityName from city where proId=?",new String[]{proId+""});
		while (cursorTemp.moveToNext()) {
			String cityName = cursorTemp.getString(cursorTemp.getColumnIndex("cityName"));
			list.add(cityName);
		}
		cursorTemp.close();
		db.close();
		dbHelper.close();
		return list;
	}

}
