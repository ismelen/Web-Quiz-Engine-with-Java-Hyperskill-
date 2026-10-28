package engine.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Entity
public class Completion {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private OffsetDateTime completedAt;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "quiz_id")
    public Quiz quiz;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    public AppUser user;

    public Completion() {
    }

    public Completion(Quiz quiz, AppUser user) {
        this.completedAt = OffsetDateTime.now();
        this.quiz = quiz;
        this.user = user;
    }

    @JsonProperty("id")
    public int getId() {
        return quiz.id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

}
