package com.ipc.binderpool;

import android.os.RemoteException;

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

	@Override
	public int del(int a, int b) throws RemoteException {
		return a - b;
	}

}
