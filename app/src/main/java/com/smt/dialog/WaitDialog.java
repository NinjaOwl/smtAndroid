package com.smt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.smt.R;
import com.smt.config.SMTApplication;

/** 等待提示框 */
public class WaitDialog extends Dialog{

	/**
	 * @param context
	 * @param textId 提示文字
	 */
	public WaitDialog(Context context, String textId) {
		super(context);
		initView(textId);
	}

	private void initView(String textId) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_wait);
		View mRootView = findViewById(R.id.rootView);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(SMTApplication.getScreenWidth() - 200, LayoutParams.WRAP_CONTENT);
		mRootView.setLayoutParams(params);
		mRootView.setBackgroundDrawable(new ColorDrawable(0x0000ff00));

		TextView warn_title = (TextView)findViewById(R.id.warn_title);
		warn_title.setText(textId);

		this.setCanceledOnTouchOutside(false);
		Window dialogWindow = getWindow();
		ColorDrawable dw = new ColorDrawable(0x0000ff00);
		dialogWindow.setBackgroundDrawable(dw);
	}

}
