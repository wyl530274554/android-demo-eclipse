package com.melon.myapp.util;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
	@SuppressWarnings("unchecked")
	/**
	 * 从以前的convertView中查找记录控件的引用，去除findViewById操作
	 * @param view	复用的convertView
	 * @param id	要缓存的子控件
	 * @return	转化后的子控件，此处使用的是泛型，强制转换为指定的返回类型， TextView tvTime = ViewHolder.get(convertView, R.id.tv_item_note_time);
	 * 				即强制转换为TextView类型，如果此子控件并非所指定类型，则异常
	 */
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
