package com.dp.hex_t_bot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class Chat {
    private static final String ID_FIELD = "id";
    private static final String TYPE_FIELD = "type";
    private static final String TITLE_FIELD = "title";
    private static final String USERNAME_FIELD = "username";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String IS_FORUM_FIELD  = "is_forum";

    private static final String USER_CHAT_TYPE = "private";
    private static final String GROUP_CHAT_TYPE = "group";
    private static final String CHANNEL_CHAT_TYPE = "channel";
    private static final String SUPERGROUP_CHAT_TYPE = "supergroup";

    @JsonProperty(ID_FIELD)
    @NonNull
    private Long id;
    /**
     * Type of the chat, one of “private”, “group” or “channel” or "supergroup"
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;
    /**
     * Optional.
     * Title of the chat, only for channels and group chat
     */
    @JsonProperty(TITLE_FIELD)
    private String title;
    /**
     * Optional
     * Interlocutor's first name for private chats
     */
    @JsonProperty(FIRST_NAME_FIELD)
    private String firstName;
    /**
     * Optional.
     * Interlocutor's last name for private chats
     */
    @JsonProperty(LAST_NAME_FIELD)
    private String lastName;
    /**
     * Optional.
     * Username of the chat, only for private chats and channels if available
     */
    @JsonProperty(USERNAME_FIELD)
    private String userName;
    /**
     * Optional.
     * True, if the supergroup chat is a forum (has topics enabled)
     */
    @JsonProperty(IS_FORUM_FIELD)
    private Boolean isForum;

    @JsonIgnore
    public Boolean isGroupChat() {
        return GROUP_CHAT_TYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isChannelChat() {
        return CHANNEL_CHAT_TYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isUserChat() {
        return USER_CHAT_TYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isSuperGroupChat() {
        return SUPERGROUP_CHAT_TYPE.equals(type);
    }
}
