package com.leap12.hipj.handlers;

import com.leap12.common.ClientConnection;

public interface HipChatMessageHandler {

	void onReceivedMsg(ClientConnection conn, String msg) throws Exception;

}
