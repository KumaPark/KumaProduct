package com.simens.us.myapplication.Network;


import com.simens.us.myapplication.Network.response.ResponseProtocol;

public interface ProtocolListener {

	public void onResponse(int tag, ResponseProtocol resProtocol);
	
}
