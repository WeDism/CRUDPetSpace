package com.pets_space.models;

import com.pets_space.models.essences.UserEssence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Message {
    private UUID messageId = UUID.randomUUID();
    private String text;
    private LocalDateTime time = LocalDateTime.now();
    private UUID author;
    private List<UUID> owners;

    public Message(String text, UUID author, List<UUID> owners) {
        this.text = text;
        this.author = author;
        this.owners = owners;
    }

    public UUID getMessageId() {
        return this.messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(this.getMessageId(), message.getMessageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMessageId());
    }
}
