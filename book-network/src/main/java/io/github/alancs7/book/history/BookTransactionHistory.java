package io.github.alancs7.book.history;

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
public class BookTransactionHistory extends BaseEntity {

    private boolean returned;
    private boolean returnApproved;

}
