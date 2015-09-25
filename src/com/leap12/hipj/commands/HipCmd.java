package com.leap12.hipj.commands;

import java.util.Comparator;

import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.databuddy.Commands.CmdResponse;
import com.leap12.databuddy.Commands.CmdResponse.CmdResponseMutable;
import com.leap12.databuddy.Commands.Command;
import com.leap12.hipj.data.HipChatRecv;
import com.leap12.hipj.data.HipChatResp;

public class HipCmd extends Command<HipChatRecv, Void> {
	public static class HipCmdRelevanceComparator implements Comparator<HipCmd> {
		private final HipChatRecv mRecv;

		public HipCmdRelevanceComparator( HipChatRecv recv ) {
			this.mRecv = recv;
		}

		@Override
		public int compare( HipCmd a, HipCmd b ) {
			float aRelevance = a.isCommand( mRecv );
			float bRelevance = b.isCommand( mRecv );
			if ( aRelevance > bRelevance ) {
				return -1;
			} else if ( aRelevance < bRelevance ) {
				return 1;
			}
			return 0;
		}
	}

	public HipCmd() {
		super( Void.class );
	}

	@Override
	public CmdResponse<Void> executeCommand( BaseConnectionDelegate connection, HipChatRecv recv ) {
		try {
			HipChatResp resp = new HipChatResp( recv );
			resp.setMessage( "You typed: " + recv.getMessageBodyNoCmd() );
			connection.writeMsg( resp.respond() );
			return new CmdResponseMutable<Void>( Void.class );
		} catch ( Exception e ) {
			return new CmdResponseMutable<Void>( Void.class, e );
		}
	}

	@Override
	public float isCommand( HipChatRecv recv ) {
		return 0.01f; // always the least likely command.
	}
}
