package com.example.ysww.snailfamily.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;


/**
 * 自定义弹窗 聊天保管时间
 */
public class CollectingChatCustodyTimeDialog extends Dialog{
	public CollectingChatCustodyTimeDialog(Context context) {
		super(context);
	}

	public CollectingChatCustodyTimeDialog(Context context, int theme) {
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
		private  OnDialogClickListener mDialogListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public interface OnDialogClickListener{
			void ReturnClickListener(View view);
			void ConfirmationClickListener(View view);
			void DateClickListener(View view, TextView date);
		}

		public void setDialogListener(OnDialogClickListener mDialogListener) {
			this.mDialogListener = mDialogListener;
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
		public CollectingChatCustodyTimeDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CollectingChatCustodyTimeDialog dialog = new CollectingChatCustodyTimeDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.coverpopup_collecting_custody_item, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the confirm button
			final TextView date = (TextView) layout.findViewById(R.id.date);
			Button return_btn = (Button) layout.findViewById(R.id.return_btn);
			Button affirm_btn = (Button) layout.findViewById(R.id.affirm_btn);
			if(mDialogListener!=null){
				date.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mDialogListener.DateClickListener(v,date);
					}
				});
				affirm_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mDialogListener.ConfirmationClickListener(v);
					}
				});
				return_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mDialogListener.ReturnClickListener(v);
					}
				});
			}

			dialog.setContentView(layout);
			return dialog;
		}

	}

}
