package com.example.bookmanager.controller;

import com.example.bookmanager.entity.Book;
import com.example.bookmanager.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 一覧表示
    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    // 新規追加フォーム
    @GetMapping("/books/form")
    public String bookForm(){
        return "book-form";
    }

    // 新規登録
    @PostMapping("/books/add")
    public String addBook(@RequestParam String title, @RequestParam String author){
        bookRepository.save(new Book(title,author));
        return "redirect:/books";
    }

    // 編集フォーム表示
    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable Long id, Model model){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book-edit";
        } else  {
            return "redirect:/books";
        }
    }

    // 編集内容保存
    @PostMapping("/books/update")
    public String updateBook(@RequestParam Long id, @RequestParam String title, @RequestParam String author){
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(title);
        book.setAuthor(author);
        bookRepository.save(book);
        return "redirect:/books";
    }

    // 削除処理
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id){
        bookRepository.deleteById(id);
        return "redirect:/books";
    }
}
