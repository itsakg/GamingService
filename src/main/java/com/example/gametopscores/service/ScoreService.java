package com.example.gametopscores.service;

import com.example.gametopscores.model.PlayerScore;

import java.util.List;

public interface ScoreService {
    void recordScore(PlayerScore playerScore);
    List<PlayerScore> getTopScores();
}
