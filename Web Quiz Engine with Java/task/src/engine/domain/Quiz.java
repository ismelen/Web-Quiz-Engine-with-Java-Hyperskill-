package engine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue
    public Integer id;

    public String title;
    public String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public List<String> options;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    public List<Integer> answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id",  nullable = false)
    public AppUser user;

    public Quiz(Integer id, String title, String text, List<String> options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Quiz() {
    }
}