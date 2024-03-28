package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private QuestionType questionType;

    @Column(nullable = true)
    private String multimediaLink;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    public Question() {}

    // Constructor with fields
    public Question(Quiz quiz, String text, QuestionType questionType, String multimediaLink, LocalDateTime creationDate) {
        this.quiz = quiz;
        this.text = text;
        this.questionType = questionType;
        this.multimediaLink = multimediaLink;
        this.creationDate = creationDate;
    }

    // QuestionType ENUM
    public enum QuestionType {
        MULTIPLE_CHOICE,
        STUDY,
        FILL_IN_BLANK
    }
}
