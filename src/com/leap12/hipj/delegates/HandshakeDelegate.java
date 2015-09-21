package com.leap12.hipj.delegates;

import com.leap12.common.Log;
import com.leap12.databuddy.BaseConnectionDelegate;

/** Verify the connection and route to the appropriate delegate */
public class HandshakeDelegate extends BaseConnectionDelegate {

	@Override
	protected void onReceivedMsg(String msg) throws Exception {
		logDebugMessageWithNewlineChars(msg); // log the incoming request for fun
		if (msg.contains("postman: debug") ||
				msg.contains("POST / HTTP")
				&& msg.contains("User-Agent: HipChat.com")
				&& msg.contains("Content-Type: application/json")) {
			Log.d("valid request");
			BotDelegate delegate = new BotDelegate();
			getClientConnection().setDelegate(delegate); // hand control over to the new delegate for future messages
			delegate.onReceivedMsg(msg); // let the delegate handle this message too
		} else {
			Log.d("invalid request");
			writeMsg("invalid");
		}
	}

}
