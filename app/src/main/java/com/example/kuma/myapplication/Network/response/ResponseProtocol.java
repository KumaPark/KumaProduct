package com.example.kuma.myapplication.Network.response;


import com.example.kuma.myapplication.Utils.KumaLog;

import org.json.JSONObject;

public abstract class ResponseProtocol {



	public ResponseProtocol()
	{
		// TODO Auto-generated constructor stub
	}
	public void setBodyAndParsing(JSONObject jsonObject)
	{
		parseJson(jsonObject);
	}
	
	
	protected abstract void parseXMLResponseData(JSONObject jsonChannels);

	
	private void parseJson(JSONObject jsonObject)
	{
		KumaLog.i(" ResponseProtocol parseJson");
		
		if(jsonObject == null) {
			return;
		}
		


		try {
			parseXMLResponseData(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
