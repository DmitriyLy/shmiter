package org.dmly.shmiter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dmly.shmiter.model.Role;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDto {
    private Long id;
    private String username;
    private Set<Role> roles;
}
