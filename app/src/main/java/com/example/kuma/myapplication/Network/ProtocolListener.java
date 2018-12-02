package com.example.kuma.myapplication.Network;


import com.example.kuma.myapplication.Network.response.ResponseProtocol;

public interface ProtocolListener {

	public void onResponse(int tag, ResponseProtocol resProtocol);
	
}
