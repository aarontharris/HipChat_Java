package com.leap12.hipj.delegates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.leap12.common.Log;
import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.databuddy.Commands.CmdResponse;
import com.leap12.hipj.commands.HipCmd;
import com.leap12.hipj.commands.HipCmd.HipCmdRelevanceComparator;
import com.leap12.hipj.commands.HipGoogleCmd;
import com.leap12.hipj.commands.HipMailCmd;
import com.leap12.hipj.commands.HipTimeCmd;
import com.leap12.hipj.data.HDao;
import com.leap12.hipj.data.HipChatRecv;

public class BotDelegate extends BaseConnectionDelegate {
	private static final HipCmd[] hipCommands = new HipCmd[] {
			new HipTimeCmd(),
			new HipCmd(),
			new HipGoogleCmd(),
			new HipMailCmd(),
	};

	@Override
	protected void onReceivedMsg( String msg ) throws Exception {
		String parts[] = msg.split( "\r\n\r\n", 2 );
		String jsonPostData = parts[1];

		HDao hdao = new HDao();
		HipChatRecv recv = hdao.toHipChatRecv( jsonPostData );

		List<HipCmd> cmds = new ArrayList<>( Arrays.asList( hipCommands ) );
		Collections.sort( cmds, new HipCmdRelevanceComparator( recv ) );
		for ( HipCmd cmd : cmds ) {
			Log.d( "%s = %s", cmd.getClass().getSimpleName(), cmd.isCommand( recv ) );
		}

		CmdResponse<Void> result = cmds.get( 0 ).executeCommand( this, recv ); // cmd should deal with the error
	}
}
