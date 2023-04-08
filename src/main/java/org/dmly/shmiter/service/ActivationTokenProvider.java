package org.dmly.shmiter.service;

import org.dmly.shmiter.model.ActivationToken;

public interface ActivationTokenProvider {
    String getNewTokenValue();

    ActivationToken getToken();
}
