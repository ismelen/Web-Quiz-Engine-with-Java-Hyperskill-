package engine.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class AnswerRequestDTO {
    @NotNull
    public List<Integer> answer;
}
