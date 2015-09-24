package com.leap12.hipj.data;

import com.google.gson.annotations.SerializedName;
import com.leap12.common.HttpResponse;
import com.leap12.common.HttpResponse.HttpStatusCode;

/** Used to respond to a HipRecv */
public class HipChatResp {
	private final transient HipChatRecv mRecv;

	@SerializedName( "color" )
	private String mColor = "green";
	@SerializedName( "message" )
	private String mMessage = "";
	@SerializedName( "notify" )
	private final boolean mNotify = false;
	@SerializedName( "message_format" )
	private final String mFormat = "text";

	public HipChatResp( HipChatRecv recv ) {
		this.mRecv = recv;
	}

	public String getColor() {
		return mColor;
	}

	public void setColor( String color ) {
		mColor = color;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage( String message ) {
		mMessage = message;
	}

	@Override
	public String toString() {
		return HDao.gson().toJson( this );
	}

	public String respond() {
		HttpResponse resp = new HttpResponse();
		resp.setStatusCode( HttpStatusCode.CODE_200 );
		resp.addHeader( "Content-Type", "application/json;charset=ISO-8859-1" );
		resp.setBody( toString() );
		return resp.toString();
	}

}
