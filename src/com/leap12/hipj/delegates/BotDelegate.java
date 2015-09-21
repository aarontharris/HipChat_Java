package com.leap12.hipj.delegates;

import com.leap12.common.HttpResponse;
import com.leap12.common.HttpResponse.HttpStatusCode;
import com.leap12.common.Log;
import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.hipj.data.HDao;
import com.leap12.hipj.data.HipChatIncomingMsg;

public class BotDelegate extends BaseConnectionDelegate {

	@Override
	protected void onReceivedMsg(String msg) throws Exception {
		Log.d("onReceivedMsg");
		String parts[] = msg.split("\r\n\r\n", 2);
		String jsonPostData = parts[1];

		HDao hdao = new HDao();
		HipChatIncomingMsg incoming = hdao.toHipChatIncomingMsg(jsonPostData);
		Log.d("We got: '%s'", hdao.toString(incoming));

		String output = "{\"color\": \"green\",\"message\": \"Hello!\", \"message_format\": \"text\", \"notify\": false }";

		HttpResponse resp = new HttpResponse();
		resp.setStatusCode(HttpStatusCode.CODE_200);
		resp.addHeader("Content-Type", "application/json;charset=ISO-8859-1");
		resp.setBody(output);

		Log.debugNewlineChars(resp.toString());

		writeMsg(resp.toString());


		// writeMsg("application/json");
		// writeMsg("HTTP/1.1 200 OK\r\n");
		// writeMsg("Server: DataBuddy/1.1\r\n");
		// writeMsg("Content-Type: application/json;charset=ISO-8859-1\r\n");
		// writeMsg(String.format("Content-Length: %s\r\n", output.length()));
		// < Date: Mon, 21 Sep 2015 01:39:05 GMT
		// writeMsg("\r\n");

		// writeMsg(output);
	}
}
