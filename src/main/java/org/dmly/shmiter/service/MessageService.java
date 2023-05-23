package org.dmly.shmiter.service;

import org.dmly.shmiter.dto.MessageDto;
import org.dmly.shmiter.model.Message;
import org.dmly.shmiter.model.User;

import java.util.List;

public interface MessageService {
    Iterable<Message> findAll();
    List<Message> findByTag(String filterTag);

    void save(MessageDto messageDto, User user);

    List<Message> findByAuthorId(Long userId);

    Message getById(Long id);
}
