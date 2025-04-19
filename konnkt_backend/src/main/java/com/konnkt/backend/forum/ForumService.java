package com.konnkt.backend.forum;

import com.konnkt.backend.exception.BadRequestException;
import com.konnkt.backend.forum.dto.PublicForumDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {
    private ForumRepository forumRepository;
    private static final Logger logger = LoggerFactory.getLogger(ForumService.class);

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public PublicForumDto getForumById(Long id) {
        Forum forum = forumRepository.findById(id).orElseThrow(() -> new BadRequestException("Forum not found"));
        PublicForumDto publicForumDto = new PublicForumDto();
        publicForumDto.setName(forum.getName());
        publicForumDto.setDescription(forum.getDescription());
        return publicForumDto;
    }

    public List<PublicForumDto> getAllForums() {
        return forumRepository.findAll().stream().map(forum -> {
            PublicForumDto publicForumDto = new PublicForumDto();
            publicForumDto.setName(forum.getName());
            publicForumDto.setDescription(forum.getDescription());
            return publicForumDto;
        }).toList();
    }

    public void createForum(PublicForumDto forum) {
        Forum newForum = new Forum();
        newForum.setName(forum.getName());
        newForum.setDescription(forum.getDescription());
        newForum.setCreatedAt(LocalDateTime.now());
        forumRepository.save(newForum);
    }

    public void updateForumName(Long id, String newName) {
        Forum forum = forumRepository.findById(id).orElse(null);
        if (forum == null)
            throw new BadRequestException("Forum not found");
        forum.setName(newName);
        forumRepository.save(forum);
    }

    public void updateForumDescription(Long id, String newDescription) {
        Forum forum = forumRepository.findById(id).orElse(null);
        if (forum == null)
            throw new BadRequestException("Forum not found");
        forum.setDescription(newDescription);
        forumRepository.save(forum);
    }

    public void deleteForum(Long id) {
        Forum forum = forumRepository.findById(id).orElse(null);
        if (forum == null)
            throw new BadRequestException("Forum not found");
        forumRepository.delete(forum);
    }
}
