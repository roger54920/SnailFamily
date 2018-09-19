package com.example.ysww.snailfamily.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.custom.XCDropDownListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * 自定义弹窗 包裹详情退回原因
 */
public class ParcelDetailsExceedTimeDialog extends Dialog {

	public ParcelDetailsExceedTimeDialog(Context context) {
		super(context);
	}

	public ParcelDetailsExceedTimeDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		public ParcelDetailsExceedTimeDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final ParcelDetailsExceedTimeDialog dialog = new ParcelDetailsExceedTimeDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.coverpopup_parcel_details_exced_time_item, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the confirm button
			spinner(layout);
			dialog.setContentView(layout);
			return dialog;
		}
		//自定义下拉框列表
		public void spinner(View view){
			XCDropDownListView dropDownListView = (XCDropDownListView)view.findViewById(R.id.drop_down_list_view);
			ArrayList<String> list = new ArrayList<String>();
			for(int i = 0;i< 6;i++){
				list.add("原因"+(i+1));
			}
			dropDownListView.setItemsData(list);
			dropDownListView.setSelectListener(new XCDropDownListView.OnSelectListener() {
				@Override
				public void OnSelectValue(String value) {
					Log.e(TAG, "OnSelectValue: "+value);
				}
			});
		}
	}
}
