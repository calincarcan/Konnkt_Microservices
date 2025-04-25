package com.konnkt.database.service;

import com.konnkt.database.dto.ForumDto;
import com.konnkt.database.dto.InputForumDto;
import com.konnkt.database.entity.Forum;
import com.konnkt.database.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ForumService implements ForumServiceInterface{
    private final ForumRepository forumRepository;

    private ForumDto EntityToDto(Forum forum) {
        return new ForumDto(forum.getForumId(), forum.getName(), forum.getDescription());
    }
    @Override
    public List<ForumDto> findAll() {
        return forumRepository.findAll()
                .stream()
                .map(this::EntityToDto)
                .toList();
    }
    @Override
    public Optional<ForumDto> findById(Long id) {
        return forumRepository.findById(id)
                .map(this::EntityToDto);
    }
    @Override
    public Optional<ForumDto> findByName(String name) {
        return forumRepository.findByName(name)
                .map(this::EntityToDto);
    }
    @Override
    public Optional<ForumDto> createForum(InputForumDto forumDto) {
        Forum forum = new Forum();
        forum.setName(forumDto.name());
        forum.setDescription(forumDto.description());
        forum.setCreatedAt(LocalDateTime.now());
        try {
            forumRepository.save(forum);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(EntityToDto(forum));
    }
    @Override
    public Optional<ForumDto> updateForumName(Long id, String name) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            Forum updatedForum = forum.get();
            updatedForum.setName(name);
            try {
                forumRepository.save(updatedForum);
            } catch (Exception e) {
                return Optional.empty();
            }
            return Optional.of(EntityToDto(updatedForum));
        }
        return Optional.empty();
    }
    @Override
    public Optional<ForumDto> updateForumDescription(Long id, String description) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            Forum updatedForum = forum.get();
            updatedForum.setDescription(description);
            try {
                forumRepository.save(updatedForum);
            } catch (Exception e) {
                return Optional.empty();
            }
            return Optional.of(EntityToDto(updatedForum));
        }
        return Optional.empty();
    }
    @Override
    public Boolean deleteForumById(Long id) {
        try {
            forumRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteForumByName(String name) {
        Optional<Forum> forum = forumRepository.findByName(name);
        if (forum.isPresent()) {
            try {
                forumRepository.delete(forum.get());
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return true;
    }
}
