package io.github.alancs7.book.feedback;

import io.github.alancs7.book.book.Book;
import io.github.alancs7.book.book.BookRepository;
import io.github.alancs7.book.exception.BookNotFoundException;
import io.github.alancs7.book.exception.OperationNotPermittedException;
import io.github.alancs7.book.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Long save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException("No book found with the ID:: " + request.bookId()));

        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("You cannot give feedback to an archived or not shareable book");
        }

        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give a feedback to your own book");
        }

        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }
}
