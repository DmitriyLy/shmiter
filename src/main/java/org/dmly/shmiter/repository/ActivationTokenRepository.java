package org.dmly.shmiter.repository;

import org.dmly.shmiter.model.ActivationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ActivationTokenRepository extends CrudRepository<ActivationToken, Long> {

    Optional<ActivationToken> findByToken(String token);
}
