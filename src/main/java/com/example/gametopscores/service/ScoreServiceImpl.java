package com.example.gametopscores.service;

import com.example.gametopscores.model.PlayerScore;
import com.example.gametopscores.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public void recordScore(PlayerScore playerScore) {
        scoreRepository.addScore(playerScore.getPlayerName(), playerScore.getScore());
    }

    @Override
    public List<PlayerScore> getTopScores() {
        Set<ZSetOperations.TypedTuple<String>> topScores = scoreRepository.getTopScores(5);
        List<PlayerScore> topPlayers = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> score : topScores) {
            topPlayers.add(new PlayerScore(score.getValue(), score.getScore().intValue()));
        }
        return topPlayers;
    }
}
