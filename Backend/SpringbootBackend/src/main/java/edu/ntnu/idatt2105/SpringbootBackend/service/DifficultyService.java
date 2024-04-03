package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.model.Difficulty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DifficultyService {

    public List<Difficulty> getAllDifficulties() {
        return Arrays.asList(Difficulty.values());
    }
}
