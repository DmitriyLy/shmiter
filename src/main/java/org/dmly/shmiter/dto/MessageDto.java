package org.dmly.shmiter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class MessageDto {
    private Long id;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 2048, message = "Message length cannot be more than 2kB")
    private String message;
    private String tag;
    private MultipartFile file;
}
