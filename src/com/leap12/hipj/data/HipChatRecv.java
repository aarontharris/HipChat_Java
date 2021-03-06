package com.leap12.hipj.data;

import com.google.gson.annotations.SerializedName;
import com.leap12.common.StrUtl;

public class HipChatRecv {

	@SerializedName( "event" )
	private String event;

	@SerializedName( "oauth_client_id" )
	private String oauthClientId;

	@SerializedName( "webhook_id" )
	private String webHookId;

	@SerializedName( "item" )
	private MsgItem item;

	public String getEvent() {
		return event;
	}

	public String getOauthClientId() {
		return oauthClientId;
	}

	public String getWebHookId() {
		return webHookId;
	}

	public MsgItem getItem() {
		return item;
	}

	private HipChatRecv getRequestData() {
		return this;
	}

	public HipChatEntity getRoomData() {
		return getRequestData().getItem().getRoom();
	}

	public String getRoomName() {
		return getRoomData().getName();
	}

	public String getRoomId() {
		return getRoomData().getId();
	}

	public HipChatMessage getMessageData() {
		return getRequestData().getItem().getMessage();
	}

	public String getMessageId() {
		return getMessageData().getId();
	}

	public String getMessageBody() {
		return getMessageData().getMessage();
	}

	public String getMessageBodyNoCmd() {
		String out = getMessageBody();
		if ( StrUtl.isNotEmpty( out ) ) {
			String[] parts = out.split( " ", 2 );
			if ( parts.length == 2 ) {
				return parts[1];
			}
		}
		return StrUtl.EMPTY;
	}

	public String getMessageDate() {
		return getMessageData().getDate();
	}

	public HipChatUser getSenderData() {
		return getMessageData().getFrom();
	}

	public String getSenderId() {
		return getSenderData().getId();
	}

	public String getSenderMentionName() {
		return getSenderData().getMentionName();
	}

	public String getSenderName() {
		return getSenderData().getName();
	}



	public static class MsgItem {
		@SerializedName( "message" )
		private HipChatMessage message;
		@SerializedName( "room" )
		private HipChatEntity room;

		public HipChatMessage getMessage() {
			return message;
		}

		public HipChatEntity getRoom() {
			return room;
		}

	}



	public static class HipChatUser extends HipChatEntity {
		@SerializedName( "mention_name" )
		private String mentionName;

		public String getMentionName() {
			return mentionName;
		}
	}



	public static class HipChatLinks {
		@SerializedName( "self" )
		private String self;
		@SerializedName( "participants" )
		private String participants;
		@SerializedName( "webhooks" )
		private String webhooks;

		public String getSelf() {
			return self;
		}

		public String getParticipants() {
			return participants;
		}

		public String getWebHooks() {
			return webhooks;
		}
	}



	public static class HipChatMessage {

		@SerializedName( "id" )
		private String id;

		@SerializedName( "type" )
		private String type;

		@SerializedName( "message" )
		private String message;

		@SerializedName( "date" )
		private String date;

		@SerializedName( "from" )
		private HipChatUser from;

		// FIXME: mentions

		public String getId() {
			return id;
		}

		public String getType() {
			return type;
		}

		public String getMessage() {
			return message;
		}

		public String getDate() {
			return date;
		}

		public HipChatUser getFrom() {
			return from;
		}
	}



	public static class HipChatEntity {
		@SerializedName( "id" )
		private String id;

		@SerializedName( "links" )
		private HipChatLinks links;

		@SerializedName( "name" )
		private String name;

		@SerializedName( "version" )
		private String version;

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getVersion() {
			return version;
		}
	}
}
