package com.leap12.hipj.delegates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.leap12.common.Log;
import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.hipj.data.HDao;
import com.leap12.hipj.data.HipChatRecv;
import com.leap12.hipj.handlers.HipCmd;
import com.leap12.hipj.handlers.HipCmd.HipCmdRelevanceComparator;
import com.leap12.hipj.handlers.HipGoogleCmd;
import com.leap12.hipj.handlers.HipTimeCmd;

public class BotDelegate extends BaseConnectionDelegate {
	private static final HipCmd[] commands = new HipCmd[] {
			new HipTimeCmd(),
			new HipCmd(),
			new HipGoogleCmd()
	};

	@Override
	protected void onReceivedMsg(String msg) throws Exception {
		String parts[] = msg.split("\r\n\r\n", 2);
		String jsonPostData = parts[1];

		HDao hdao = new HDao();
		HipChatRecv recv = hdao.toHipChatRecv(jsonPostData);

		List<HipCmd> cmds = new ArrayList<>(Arrays.asList(commands));
		Collections.sort(cmds, new HipCmdRelevanceComparator(recv));
		for (HipCmd cmd : cmds) {
			Log.d("%s = %s", cmd.getClass().getSimpleName(), cmd.isCommand(recv));
		}
		cmds.get(0).executeCommand(this, recv);
	}
}
