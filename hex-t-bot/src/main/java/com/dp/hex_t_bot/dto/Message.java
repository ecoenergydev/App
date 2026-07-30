package com.dp.hex_t_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private static final String MESSAGE_ID_FIELD = "message_id";
    private static final String MESSAGE_THREAD_ID_FIELD = "message_thread_id";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String TEXT_FIELD = "text";

    @JsonProperty(MESSAGE_ID_FIELD)
    private Integer messageId;
    /**
     * Optional.
     * Unique identifier of a message thread or forum topic to which the message belongs;
     * for supergroups and private chats only
     */
    @JsonProperty(MESSAGE_THREAD_ID_FIELD)
    private Integer messageThreadId;
    /**
     * Optional.
     * Sender of the message; may be empty for messages sent to channels.
     * For backward compatibility, if the message was sent on behalf of a chat, the field contains a fake sender user
     * in non-channel chats
     */
    @JsonProperty(FROM_FIELD)
    private User from;
    /**
     * Date the message was sent in Unix time. It is always a positive number, representing a valid date.
     */
    @JsonProperty(DATE_FIELD)
    private Integer date;
    /**
     * Conversation the message belongs to
     */
    @JsonProperty(CHAT_FIELD)
    private Chat chat;
    /**
     * Optional.
     * For forwarded messages, sender of the original message
     */
    @JsonProperty(TEXT_FIELD)
    private String text;
    /**
     * Optional.
     * For text messages, special entities like usernames, URLs,
     * bot commands, etc. that appear in the text
     */
}
