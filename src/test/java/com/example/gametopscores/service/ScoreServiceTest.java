package com.example.gametopscores.service;

import com.example.gametopscores.model.PlayerScore;
import com.example.gametopscores.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ScoreServiceImpl scoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Positive Test Cases

    @Test
    void recordScore_ShouldSaveScoreSuccessfully() {
        // Arrange
        PlayerScore playerScore = new PlayerScore("testuser", 100);

        // Act
        scoreService.recordScore(playerScore);

        // Assert
        verify(scoreRepository, times(1)).addScore(playerScore.getPlayerName(), playerScore.getScore());
    }

    @Test
    void getTopScores_ShouldReturnTopScores() {
        // Arrange
        Set<ZSetOperations.TypedTuple<String>> mockScores = new TreeSet<>((a, b) -> Double.compare(b.getScore(), a.getScore()));
        mockScores.add(new TestTypedTuple("user1", 150.0));
        mockScores.add(new TestTypedTuple("user2", 100.0));

        when(scoreRepository.getTopScores(5)).thenReturn(mockScores);

        // Act
        List<PlayerScore> topScores = scoreService.getTopScores();

        // Assert
        assertNotNull(topScores);
        assertEquals(2, topScores.size(), "There should be 2 top scores.");
        assertTrue(topScores.contains(new PlayerScore("user1", 150)), "User with score 150 should be included.");
        assertTrue(topScores.contains(new PlayerScore("user2", 100)), "User with score 100 should be included.");
    }

    // Negative Test Cases

    @Test
    void recordScore_ShouldHandleExceptionWhenAddingScore() {
        // Arrange
        PlayerScore playerScore = new PlayerScore("testuser", 100);
        doThrow(new RuntimeException("Database error")).when(scoreRepository).addScore(playerScore.getPlayerName(), playerScore.getScore());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> scoreService.recordScore(playerScore));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void getTopScores_ShouldHandleEmptyScores() {
        // Arrange
        when(scoreRepository.getTopScores(5)).thenReturn(new TreeSet<>());

        // Act
        List<PlayerScore> topScores = scoreService.getTopScores();

        // Assert
        assertNotNull(topScores);
        assertTrue(topScores.isEmpty(), "The top scores list should be empty when no scores are available.");
    }

    // Concrete implementation of TypedTuple for testing
    private static class TestTypedTuple implements ZSetOperations.TypedTuple<String> {
        private final String value;
        private final Double score;

        public TestTypedTuple(String value, Double score) {
            this.value = value;
            this.score = score;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public Double getScore() {
            return score;
        }

        @Override
        public int compareTo(ZSetOperations.TypedTuple<String> o) {
            if (this.getScore() == null) return 1;
            if (o.getScore() == null) return -1;
            return Double.compare(this.getScore(), o.getScore());
        }
    }
}
