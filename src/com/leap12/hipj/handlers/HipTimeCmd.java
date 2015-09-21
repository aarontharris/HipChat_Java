package com.leap12.hipj.handlers;

import java.util.Date;
import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.databuddy.Commands.CmdResponse;
import com.leap12.databuddy.Commands.RequestStatus;
import com.leap12.hipj.data.HipChatRecv;
import com.leap12.hipj.data.HipChatResp;

public class HipTimeCmd extends HipCmd {

	public HipTimeCmd() {
		super();
	}

	@Override
	public CmdResponse<Void> executeCommand(BaseConnectionDelegate connection, HipChatRecv recv) {
		try {
			HipChatResp resp = new HipChatResp(recv);
			resp.setMessage(new Date().toString());
			connection.writeMsg(resp.respond());
			return new CmdResponse<Void>(Void.class, null, RequestStatus.SUCCESS);
		} catch (Exception e) {
			return new CmdResponse<Void>(Void.class, null, RequestStatus.FAIL_UNKNOWN);
		}
	}

	@Override
	public float isCommand(HipChatRecv recv) {
		return computeRelevance(recv);
	}

	private float computeRelevance(HipChatRecv recv) {
		String msg = recv.getMessageBodyNoCmd().toLowerCase();
		float out = 0.0f;
		if (msg.equals("what time is it")) {
			out += 1.0f;
		} else if (msg.contains("time") || msg.contains("hour")) {
			out += 0.5f;
			if (msg.contains("what")) {
				out += 0.2f;
			}
			if (msg.contains("is")) {
				out += 0.1f;
			}
		}
		return out;
	}
}
