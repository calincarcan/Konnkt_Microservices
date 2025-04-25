package com.konnkt.database.service;

import com.konnkt.database.dto.*;
import com.konnkt.database.entity.*;
import java.util.*;

public interface VoteServiceInterface {
    List<VoteDto> findAll();
    Optional<VoteDto> findById(Long id);
    Optional<VoteDto> createVote(InputVoteDto inputVoteDto);
    Optional<VoteDto> changeVote(Long id);
    Boolean deleteVote(Long id);
}