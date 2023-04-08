package org.dmly.shmiter.service;

import org.dmly.shmiter.dto.CreateUserDto;
import org.dmly.shmiter.dto.UserActionResult;

public interface RegistrationService {

    UserActionResult register(CreateUserDto createUserDto);

    UserActionResult activate(String tokenValue);

}
