package com.leap12.hipj;

import com.leap12.common.Log;
import com.leap12.databuddy.DataBuddy;
import com.leap12.hipj.delegates.HandshakeDelegate;

public class HipChatJava {
	public static void main( String args[] ) {
		try {
			Runtime.getRuntime().addShutdownHook( new Thread() {
				@Override
				public void run() {
					DataBuddy.get().shutdown();
				}
			} );

			DataBuddy dataBuddy = DataBuddy.get();
			dataBuddy.startup( HandshakeDelegate.class );
		} catch ( Exception e ) {
			Log.e( e, "Trouble during startup" );
		}
	}
}
