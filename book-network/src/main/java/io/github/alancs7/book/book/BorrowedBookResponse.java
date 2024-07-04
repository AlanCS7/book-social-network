package io.github.alancs7.book.book;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse {

    private Long id;
    private String title;
    private String authorName;
    private String isbn;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
