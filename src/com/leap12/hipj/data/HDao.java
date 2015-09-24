package com.leap12.hipj.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leap12.common.Log;

public class HDao {
	private static final Gson gson = new GsonBuilder().create();

	public static final Gson gson() {
		return gson;
	}

	public HDao() {
	}

	public HipChatRecv toHipChatRecv( String jsonString ) {
		return gson.fromJson( jsonString, HipChatRecv.class );
	}

	public String toString( HipChatRecv msg ) {
		return gson.toJson( msg );
	}

	public static void test() {
		HDao dao = new HDao();
		String msg = "{\"event\": \"room_message\", \"item\": {\"message\": {\"date\": \"2015-09-20T23:10:34.900443+00:00\", \"from\": {\"id\": 2170682, \"links\": {\"self\": \"https://api.hipchat.com/v2/user/2170682\"}, \"mention_name\": \"aaron\", \"name\": \"Aaron Harris\", \"version\": \"00000000\"}, \"id\": \"140dbb1d-9a15-4784-bcaf-58e2bb89dd43\", \"mentions\": [], \"message\": \"/computer test\", \"type\": \"message\"}, \"room\": {\"id\": 1535857, \"links\": {\"participants\": \"https://api.hipchat.com/v2/room/1535857/participant\", \"self\": \"https://api.hipchat.com/v2/room/1535857\", \"webhooks\": \"https://api.hipchat.com/v2/room/1535857/webhook\"}, \"name\": \"test\", \"version\": \"KLK6V6K4\"}}, \"oauth_client_id\": \"a3890fb6-b268-4fef-a3ac-eb71cdc7a429\", \"webhook_id\": 2609978}";

		HipChatRecv in = dao.toHipChatRecv( msg );
		Log.d( "We got: %s", in.getItem().getMessage().getFrom().getName() );

		String serialized = dao.toString( in );
		Log.d( "serialized; '%s", serialized );

		in = dao.toHipChatRecv( serialized );
		Log.d( "We got: %s", in.getItem().getMessage().getFrom().getName() );
	}
}
