package io.github.alancs7.book.feedback;

import io.github.alancs7.book.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Feedback extends BaseEntity {

    private Double vote;
    private String comment;

}
