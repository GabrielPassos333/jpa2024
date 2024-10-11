package com.bookstore.jpa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table (name = "TB_PUBLISHER")
public class PublisherModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    //Uma editora pode ter vários livros
    //WRITE_ONLY -> só vai ser possível escrever, não vai ser possível ler, pois não faz sentido ler os livros de uma editora
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    //mappedBy = "publisher" -> é o nome do atributo que está na classe BookModel
    //fetch = FetchType.LAZY -> só vai carregar os livros quando for solicitado
    //LAZY -> só vai carregar os livros quando for solicitado
    //EAGER -> vai carregar todos os livros de uma vez
    private Set<BookModel> books = new HashSet<>();//uma coleção da endidade BookModel


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookModel> getBooks() {
        return books;
    }

    public void setBooks(Set<BookModel> books) {
        this.books = books;
    }
}
