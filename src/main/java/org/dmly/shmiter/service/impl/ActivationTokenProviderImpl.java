package org.dmly.shmiter.service.impl;

import org.dmly.shmiter.model.ActivationToken;
import org.dmly.shmiter.service.ActivationTokenProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ActivationTokenProviderImpl implements ActivationTokenProvider {
    @Override
    public String getNewTokenValue() {
        return UUID.randomUUID().toString();
    }

    @Override
    public ActivationToken getToken() {
        return new ActivationToken(getNewTokenValue(), LocalDateTime.now());
    }
}
