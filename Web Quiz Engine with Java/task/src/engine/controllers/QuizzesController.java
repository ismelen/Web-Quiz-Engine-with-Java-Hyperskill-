package engine.controllers;

import engine.domain.*;
import engine.repositories.CompletionRepository;
import engine.repositories.QuizRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizzesController {
    private final QuizRepository quizRepo;
    private final CompletionRepository completionRepo;

    @Autowired
    public QuizzesController(QuizRepository quizRepository, CompletionRepository completionRepository) {
        this.quizRepo = quizRepository;
        this.completionRepo = completionRepository;
    }

    @PostMapping
    public Quiz createQuiz(@AuthenticationPrincipal AppUserAdapter user, @RequestBody @Valid CreateQuizDTO dto) {
        Quiz quiz = new Quiz();
        quiz.answer = dto.answer == null ? List.of() : dto.answer;
        quiz.options = dto.options == null ? List.of() : dto.options;
        quiz.title = dto.title;
        quiz.text = dto.text;
        quiz.user = user.getUser();

        return this.quizRepo.save(quiz);
    }

    @GetMapping("{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return this.quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Page<Quiz> getQuizzes(@RequestParam @Min(0) Integer page) {
        return this.quizRepo.findAll(PageRequest.of(page, 10));
    }

    @GetMapping("/completed")
    public Page<Completion> getCompletedQuizzes(
        @AuthenticationPrincipal AppUserAdapter user,
        @RequestParam @Min(0) Integer page
    ) {
        return completionRepo.findAllByUser(
            user.getUser(),
            PageRequest.of(page, 10, Sort.by(
                Sort.Direction.DESC,
                "completedAt"
            ))
        );
    }

    @PostMapping("{id}/solve")
    public QuizAnswerResponseDTO solveQuiz(
        @AuthenticationPrincipal AppUserAdapter user,
        @PathVariable int id,
        @RequestBody @Valid AnswerRequestDTO dto)
    {
        Quiz quiz = this.quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Set<Integer> expected = new HashSet<>(quiz.answer);
        Set<Integer> given =  new HashSet<>(dto.answer);


        if (expected.equals(given)) {
            completionRepo.save(new Completion(quiz, user.getUser()));
            return new QuizAnswerResponseDTO(true, "Congratulations, you're right!");
        }

        return new QuizAnswerResponseDTO(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@AuthenticationPrincipal AppUserAdapter user, @PathVariable int id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!quiz.user.getEmail().equals(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);


        quizRepo.deleteById(id);
    }


}
