package com.konnkt.backend.service;

import com.konnkt.backend.entity.Vote;
import java.util.List;

public interface VoteServiceInterface {
    List<Vote> findAll();
    Vote findById(Long id);
    Vote create(Vote vote);
    Vote update(Long id, Vote vote);
    void delete(Long id);
}