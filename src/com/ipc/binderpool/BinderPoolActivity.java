package com.ipc.binderpool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;

public class BinderPoolActivity extends Activity {
	private static final String TAG = "BinderPoolActivity";

	private ISecurityCenter mSecurityCenter;
	private ICompute mCompute;
	private EditText binderTest;
	private StringBuilder strBuilder;
	private EditText binderCount;
	private StringBuilder countBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_binder_pool);
		binderTest = (EditText) findViewById(R.id.binder_test);
		strBuilder = new StringBuilder();
		binderCount = (EditText) findViewById(R.id.binder_count);
		countBuilder = new StringBuilder();
		new Thread(new Runnable() {

			@Override
			public void run() {
				doWork();
			}
		}).start();
	}

	private void doWork() {
		BinderPool binderPool = BinderPool.getInsance(BinderPoolActivity.this);
		IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
		;
		mSecurityCenter = (ISecurityCenter) SecurityCenterImpl.asInterface(securityBinder);
		Log.d(TAG, "visit ISecurityCenter");
		String msg = "helloworld-安卓";
		System.out.println("content:" + msg);
		strBuilder.append("visit ISecurityCenter");
		strBuilder.append("\n");
		strBuilder.append("content:" + msg);
		strBuilder.append("\n");
		binderTest.setText(strBuilder.toString());
		try {
			String password = mSecurityCenter.encrypt(msg);
			System.out.println("encrypt:" + password);
			System.out.println("decrypt:" + mSecurityCenter.decrypt(password));
			strBuilder.append("encrypt:" + password);
			strBuilder.append("\n");
			strBuilder.append("decrypt:" + mSecurityCenter.decrypt(password));
			strBuilder.append("\n");
			binderTest.setText(strBuilder.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		Log.d(TAG, "visit ICompute");
		countBuilder.append("visit ICompute");
		countBuilder.append("\n");
		binderCount.setText(countBuilder.toString());
		IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
		;
		mCompute = ComputeImpl.asInterface(computeBinder);
		try {
			System.out.println("3+5=" + mCompute.add(3, 5));
			countBuilder.append("3+5=" + mCompute.add(3, 5));
			countBuilder.append("\n");
			binderCount.setText(countBuilder.toString());
			System.out.println("30-5=" + mCompute.del(30, 5));
			countBuilder.append("30-5=" + mCompute.del(30, 5));
			countBuilder.append("\n");
			binderCount.setText(countBuilder.toString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
