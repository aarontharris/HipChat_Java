package com.leap12.hipj.commands;

import java.util.Date;

import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.databuddy.Commands.CmdResponse;
import com.leap12.databuddy.Commands.CmdResponse.CmdResponseMutable;
import com.leap12.hipj.data.HipChatRecv;
import com.leap12.hipj.data.HipChatResp;

public class HipTimeCmd extends HipCmd {

	public HipTimeCmd() {
		super();
	}

	@Override
	public CmdResponse<Void> executeCommand( BaseConnectionDelegate connection, HipChatRecv recv ) {
		try {
			HipChatResp resp = new HipChatResp( recv );
			resp.setMessage( new Date().toString() );
			connection.writeMsg( resp.respond() );
			return new CmdResponseMutable<Void>( Void.class );
		} catch ( Exception e ) {
			return new CmdResponseMutable<Void>( Void.class, e );
		}
	}

	@Override
	public float isCommand( HipChatRecv recv ) {
		return computeRelevance( recv );
	}

	private float computeRelevance( HipChatRecv recv ) {
		String msg = recv.getMessageBodyNoCmd().toLowerCase();
		float out = 0.0f;
		if ( msg.equals( "what time is it" ) ) {
			out += 1.0f;
		} else if ( msg.contains( "time" ) || msg.contains( "hour" ) ) {
			out += 0.5f;
			if ( msg.contains( "what" ) ) {
				out += 0.2f;
			}
			if ( msg.contains( "is" ) ) {
				out += 0.1f;
			}
		}
		return out;
	}
}
