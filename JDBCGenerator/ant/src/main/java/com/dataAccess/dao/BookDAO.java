package com.dataAccess.dao;

import com.dataAccess.bean.Book;
import java.lang.String;
import java.util.List;

public interface BookDAO {
  List<Book> getAllBooks();

  List<Book> getBooksByRussell(String author);
}
