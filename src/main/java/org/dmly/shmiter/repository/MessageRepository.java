package org.dmly.shmiter.repository;

import org.dmly.shmiter.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);

    List<Message> findByAuthorId(Long id);
}
