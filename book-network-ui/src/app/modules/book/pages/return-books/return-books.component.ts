import { Component, OnInit } from '@angular/core';
import { PageResponseBorrowedBookResponse } from "../../../../services/models/page-response-borrowed-book-response";
import { BorrowedBookResponse } from "../../../../services/models/borrowed-book-response";
import { BookService } from "../../../../services/services/book.service";

@Component({
  selector: 'app-return-books',
  templateUrl: './return-books.component.html',
  styleUrls: ['./return-books.component.scss']
})
export class ReturnBooksComponent implements OnInit {

  returnedBooks: PageResponseBorrowedBookResponse = {};
  page: number = 0;
  size: number = 5;
  message: string = '';
  level: string = 'success';

  constructor(
    private bookService: BookService
  ) {
  }

  ngOnInit(): void {
    this.findAllReturnedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllReturnedBooks();
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllReturnedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllReturnedBooks();
  }

  goToLastPage() {
    this.page = this.returnedBooks.totalPages as number - 1;
    this.findAllReturnedBooks();
  }

  approveBookReturn(book: BorrowedBookResponse) {
    if (!book.returned) {
      this.level = 'error';
      this.message = 'The book is not yet returned';
      return;
    }
    this.bookService.approveReturnBorrowBook({
      'book-id': book.id as number
    }).subscribe({
      next: () => {
        this.level = 'success';
        this.message = 'Book return approved';
        this.findAllReturnedBooks();
      }
    })
  }

  private findAllReturnedBooks() {
    this.bookService.findAllReturnedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.returnedBooks = resp;
      }
    })
  }
}
