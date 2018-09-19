package com.example.ysww.snailfamily.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;


/**
 * 自定义弹窗 选择站点
 */
public class ShoppingSelectSiteDialog extends Dialog {

	public ShoppingSelectSiteDialog(Context context) {
		super(context);
	}

	public ShoppingSelectSiteDialog(Context context, int theme) {
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


		private String siteName;
		private String siteDetailsAddress;

		public Builder(Context context,String siteName,String siteDetailsAddress) {
			this.context = context;
			this.siteName = siteName;
			this.siteDetailsAddress = siteDetailsAddress;
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
		public ShoppingSelectSiteDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final ShoppingSelectSiteDialog dialog = new ShoppingSelectSiteDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.coverpopup_shoppping_select_site_item, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the confirm button

			Button cancel_btn = (Button) layout.findViewById(R.id.cancel_btn);
			Button confirm_btn = (Button) layout.findViewById(R.id.confirm_btn);
			TextView site_name = layout.findViewById(R.id.site_name);
			TextView site_details_address = layout.findViewById(R.id.site_details_address);
			site_name.setText(siteName);
			site_details_address.setText(siteDetailsAddress);

			// set the confirm button
			if (positiveButtonText != null) {
				confirm_btn
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					confirm_btn
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.confirm_btn).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				cancel_btn
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					cancel_btn
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.cancel_btn).setVisibility(
						View.GONE);
			}

			dialog.setContentView(layout);
			return dialog;
		}
	}
}
