package engine.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateQuizDTO {
    @NotEmpty
    public String title;

    @NotEmpty
    public String text;

    @NotNull
    @Size(min = 2)
    public List<String> options;

    public List<Integer> answer;
}