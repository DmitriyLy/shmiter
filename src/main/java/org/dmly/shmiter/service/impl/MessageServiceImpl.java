package org.dmly.shmiter.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.dmly.shmiter.dto.MessageDto;
import org.dmly.shmiter.model.Message;
import org.dmly.shmiter.model.User;
import org.dmly.shmiter.repository.MessageRepository;
import org.dmly.shmiter.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findByTag(String filterTag) {
        return messageRepository.findByTag(filterTag);
    }

    @Override
    public List<Message> findByAuthorId(Long userId) {
        return messageRepository.findByAuthorId(userId);
    }

    @Override
    public Message getById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public void save(MessageDto messageDto, User user) {
        String fileName = null;

        if (messageDto.getFile() != null) {
            try {
                fileName = getAttachmentFilename(messageDto.getFile()).orElse(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (messageDto.getId() != null) {
            Message message = messageRepository.findById(messageDto.getId()).orElseThrow(() -> new RuntimeException("Message not found"));
            if (messageDto.getMessage() != null) {
                message.setMessage(messageDto.getMessage());
            }

            if (messageDto.getTag() != null) {
                message.setTag(messageDto.getTag());
            }

            if (fileName != null) {
                message.setFile(fileName);
            }

            messageRepository.save(message);
        } else {
            messageRepository.save(new Message(messageDto.getMessage(), messageDto.getTag(), user, fileName));
        }
    }

    private Optional<String> getAttachmentFilename(MultipartFile file) throws IOException {

        if (file == null) {
            return Optional.empty();
        }

        Path uploadDirPath = Path.of(System.getProperty("user.dir"), uploadDir);
        if (Files.notExists(uploadDirPath)) {
            Files.createDirectory(uploadDirPath);
        }

        String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();

        Files.write(Path.of(System.getProperty("user.dir"), uploadDir, fileName), file.getBytes());

        return Optional.of(fileName);
    }
}
