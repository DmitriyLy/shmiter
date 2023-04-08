package org.dmly.shmiter.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ActivationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private LocalDateTime creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usr_id", referencedColumnName = "id")
    private User user;

    public ActivationToken() {
    }

    public ActivationToken(String token, LocalDateTime creationDate) {
        this.token = token;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String toke) {
        this.token = toke;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
