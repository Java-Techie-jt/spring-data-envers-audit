package com.javatechie.audit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class SpringDataEnversAuditApplication {

    @Autowired
    private BookRepository repository;


    @PostMapping("/saveBook")
    public Book saveBook(@RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping("/update/{id}/{pages}")
    public String updateBook(@PathVariable int id, @PathVariable int pages) {
        Book book = repository.findById(id).get();
        book.setPages(pages);
        repository.save(book);
        return "nook updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        repository.deleteById(id);
        return "book deleted";
    }
    @GetMapping("/getInfo/{id}")
    public void getInfo(@PathVariable  int id){
        System.out.println(repository.findLastChangeRevision(id));
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringDataEnversAuditApplication.class, args);
    }

}
