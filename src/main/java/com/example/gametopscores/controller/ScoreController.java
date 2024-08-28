package com.example.gametopscores.controller;

import com.example.gametopscores.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import org.springframework.data.redis.core.ZSetOperations;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    @PostMapping("/add")
    public String addScore(@RequestParam String playerName, @RequestParam double score) {
        scoreRepository.addScore(playerName, score);
        return "Score added for " + playerName;
    }

    @GetMapping("/top")
    public Set<ZSetOperations.TypedTuple<String>> getTopScores(@RequestParam int topN) {
        return scoreRepository.getTopScores(topN);
    }

    @GetMapping("/player")
    public Double getPlayerScore(@RequestParam String playerName) {
        return scoreRepository.getPlayerScore(playerName);
    }

    @DeleteMapping("/clear")
    public String clearScores() {
        scoreRepository.clearScores();
        return "All scores cleared";
    }
    @GetMapping("/test")
    public String testEndpoint() {
        return "Endpoint is working!";
    }

}
