package com.example.gametopscores.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ScoreRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ZSetOperations<String, String> zSetOperations;

    public ScoreRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    /**
     * Adds a score for a player to the sorted set.
     *
     * @param playerName the name of the player
     * @param score      the score to add
     */
    public void addScore(String playerName, double score) {
        zSetOperations.add("game:scores", playerName, score);
    }

    /**
     * Retrieves the top N scores from the sorted set.
     *
     * @param topN the number of top scores to retrieve
     * @return a set of player names with their scores
     */
    public Set<ZSetOperations.TypedTuple<String>> getTopScores(int topN) {
        return zSetOperations.reverseRangeWithScores("game:scores", 0, topN - 1);
    }

    /**
     * Retrieves the score of a specific player.
     *
     * @param playerName the name of the player
     * @return the score of the player
     */
    public Double getPlayerScore(String playerName) {
        return zSetOperations.score("game:scores", playerName);
    }

    /**
     * Deletes all scores from the sorted set.
     */
    public void clearScores() {
        redisTemplate.delete("game:scores");
    }
}
