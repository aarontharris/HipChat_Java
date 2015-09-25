package com.leap12.hipj.commands;

import com.leap12.databuddy.BaseConnectionDelegate;
import com.leap12.databuddy.Commands.CmdResponse;
import com.leap12.databuddy.Commands.CmdResponse.CmdResponseMutable;
import com.leap12.hipj.data.HipChatRecv;
import com.leap12.hipj.data.HipChatResp;
import com.leap12.hipj.util.Fetcher;

public class HipGoogleCmd extends HipCmd {

	public HipGoogleCmd() {
		super();
	}

	@Override
	public CmdResponse<Void> executeCommand( BaseConnectionDelegate connection, HipChatRecv recv ) {
		try {
			HipChatResp resp = new HipChatResp( recv );
			Fetcher fetcher = new Fetcher();
			String msg = fetcher.fetch( "https://en.wikipedia.org/w/api.php?format=json&action=query&titles=India&prop=revisions&rvprop=content" );
			resp.setMessage( msg );
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
		if ( msg.startsWith( "google" ) ) {
			out += 1.0f;
		}
		return out;
	}
}
