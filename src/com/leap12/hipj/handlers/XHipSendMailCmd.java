package com.leap12.hipj.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.leap12.common.Log;
import com.leap12.common.SendMail;
import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.databuddy.Commands.CmdResponse;
import com.leap12.databuddy.Commands.RequestStatus;
import com.leap12.hipj.data.HipChatRecv;
import com.leap12.hipj.data.HipChatResp;

public class XHipSendMailCmd extends HipCmd {
	private static final String patternString = "sendmail \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"";
	private static final String help = "\"subject\" \"body\" \"to,to,to\"";

	public XHipSendMailCmd() {
		super();
	}

	@Override
	public CmdResponse<Void> executeCommand(BaseConnectionDelegate connection, HipChatRecv recv) {
		HipChatResp resp = new HipChatResp(recv);
		try {
			String msg = recv.getMessageBodyNoCmd();
			Pattern p = Pattern.compile(patternString);
			Matcher m = p.matcher(msg);
			if (m.find()) {
				String subject = m.group(1);
				String body = m.group(2);
				List<String> to = new ArrayList<>();
				String recipients = m.group(3);

				if (recipients.endsWith(",")) {
					recipients.substring(0, recipients.length() - 1);
				}
				if (recipients.contains(",")) {
					to.addAll(Arrays.asList(recipients.split(",")));
				} else {
					to.add(recipients);
				}

				SendMail sendMail = new SendMail("hipchat.computer@gmail.com", "");
				sendMail.setSubject(subject);
				sendMail.setBody(body);
				sendMail.setRecipients(to);
				sendMail.send();
				resp.setMessage("msg sent");
			} else if (recv.getMessageBodyNoCmd().equals("sendmail help")) {
				resp.setMessage("sendmail " + help);
			} else {
				resp.setMessage("msg not sent, invalid pattern, try: " + help);
			}

			connection.writeMsg(resp.respond());
			return new CmdResponse<Void>(Void.class, null, RequestStatus.SUCCESS);
		} catch (Exception e) {
			try {
				resp.setMessage("Error: " + e.getMessage() + "\n"
						+ "msg not sent, invalid pattern, try: " + help);
				connection.writeMsg(resp.respond());
			} catch (Exception e2) {
				Log.e(e2);
			}
			return new CmdResponse<Void>(Void.class, null, RequestStatus.FAIL_UNKNOWN, e);
		}
	}

	// /computer sendmail "subject" "body" "from" "to,to,to"
	@Override
	public float isCommand(HipChatRecv recv) {
		String msg = recv.getMessageBodyNoCmd();
		if (Pattern.matches(patternString, msg)) {
			return 1f;
		} else if (recv.getMessageBodyNoCmd().toLowerCase().equals("sendmail help")) {
			return 1f;
		} else if (recv.getMessageBodyNoCmd().toLowerCase().startsWith("sendmail")) {
			return 0.5f;
		} else if (recv.getMessageBodyNoCmd().toLowerCase().contains("sendmail")) {
			return 0.11f;
		}
		return 0f;
	}
}
