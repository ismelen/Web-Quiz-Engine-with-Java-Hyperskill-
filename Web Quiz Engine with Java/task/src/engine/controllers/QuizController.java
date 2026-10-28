package engine.controllers;

import engine.domain.Quiz;
import engine.domain.QuizAnswerResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/quiz")
public class QuizController {
    @GetMapping()
    public Quiz getQuiz() {
        return new Quiz(
                0,
                "The Java Logo",
                "What is depicted on the Java logo?",
                List.of("Robot", "Tea leaf", "Cup of cofee", "Bug"),
                List.of(0)
        );
    }

    @PostMapping()
    public QuizAnswerResponseDTO postQuiz(@RequestParam int answer) {
        if (answer == 2) {
            return new QuizAnswerResponseDTO(true, "Congratulations, you're right!");
        }

        return new QuizAnswerResponseDTO(false, "Wrong answer! Please, try again.");
    }
}

