package com.konnkt.database.service;

import com.konnkt.database.entity.Vote;
import java.util.List;

public interface VoteServiceInterface {
    List<Vote> findAll();
    Vote findById(Long id);
    Vote create(Vote vote);
    Vote update(Long id, Vote vote);
    void delete(Long id);
}