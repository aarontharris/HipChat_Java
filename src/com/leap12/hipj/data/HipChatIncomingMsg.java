package com.leap12.hipj.data;

import com.google.gson.annotations.SerializedName;

public class HipChatIncomingMsg {

	@SerializedName("event")
	private String event;

	@SerializedName("oauth_client_id")
	private String oauthClientId;

	@SerializedName("webhook_id")
	private String webHookId;

	@SerializedName("item")
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

	public static class MsgItem {
		@SerializedName("message")
		private HipChatMessage message;
		@SerializedName("room")
		private HipChatEntity room;

		public HipChatMessage getMessage() {
			return message;
		}

		public HipChatEntity getRoom() {
			return room;
		}

	}

	public static class HipChatUser extends HipChatEntity {
		@SerializedName("mention_name")
		private String mentionName;

		public String getMentionName() {
			return mentionName;
		}
	}

	public static class HipChatLinks {
		@SerializedName("self")
		private String self;
		@SerializedName("participants")
		private String participants;
		@SerializedName("webhooks")
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
		@SerializedName("date")
		private String date;

		@SerializedName("id")
		private String id;

		@SerializedName("message")
		private String message;

		@SerializedName("type")
		private String type;

		@SerializedName("from")
		private HipChatUser from;

		// FIXME: mentions

		public String getDate() {
			return date;
		}

		public String getId() {
			return id;
		}

		public String getType() {
			return type;
		}

		public HipChatUser getFrom() {
			return from;
		}
	}

	public static class HipChatEntity {
		@SerializedName("id")
		private String id;

		@SerializedName("links")
		private HipChatLinks links;

		@SerializedName("name")
		private String name;

		@SerializedName("version")
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
