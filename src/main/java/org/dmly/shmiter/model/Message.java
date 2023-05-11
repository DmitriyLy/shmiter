package org.dmly.shmiter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 2048, message = "Message length cannot be more than 2kB")
    private String message;
    private String tag;
    private String file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Message() {
    }

    public Message(String message, String tag, User user, String file) {
        this.message = message;
        this.tag = tag;
        this.author = user;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorName() {
        if (author == null) {
            return "<none>";
        }
        return author.getUsername();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
