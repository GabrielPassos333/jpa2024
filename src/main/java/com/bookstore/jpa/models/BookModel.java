package com.bookstore.jpa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.engine.internal.Cascade;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TB_BOOK")
public class BookModel implements Serializable { //Serializabe significa que se puede guardar en un archivo
    private static final long serialVersionUID = 1L; //Identificador de la clase

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne //(fetch = FetchType.LAZY) //Muitos livros para uma editora
    @JoinColumn(name = "publisher_id") //chave estrangeira relacionada ao TB_PUBLISHER
    private PublisherModel publisher; //Vai ser uma coluna a mais que vai estar relacionada com a tabela TB_PUBLISHER

    //MANY TO MANY -> muitos para muitos Um livro pode ter mais de um autor
    //um autor pode ter mais de um livro

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Remover junto com FetchType.LAZY
    @ManyToMany//(fetch = FetchType.LAZY) //LAZY -> só vai carregar os autores quando for solicitado
    @JoinTable( //gera uma tabela de relacionamento auxiliar
            name = "tb_book_author", //nome da tabela que vai ser criada
            joinColumns = @JoinColumn(name = "book_id"), //chave estrangeira relacionada ao TB_BOOK
            inverseJoinColumns = @JoinColumn(name = "author_id")) //chave estrangeira relacionada ao TB_AUTHOR
    private Set<AuthorModel> authors = new HashSet<>(); //uma coleção da endidade AuthorModel

    //ONE TO ONE -> um para um

    //Um BOOKMODEL vai ter UM BOOK REVIEW
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)//CascadeType.ALL -> se eu apagar um livro, eu apago a review
    //Se salvar um livro, ao mesmo tempo, vai passar internamente o Review. Uma cascata de transações.
    //se deletar um Book, automaticamente, vai deletar todo o review do book
    private ReviewModel review;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PublisherModel getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherModel publisher) {
        this.publisher = publisher;
    }

    public Set<AuthorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorModel> authors) {
        this.authors = authors;
    }

    public ReviewModel getReview() {
        return review;
    }

    public void setReview(ReviewModel review) {
        this.review = review;
    }
}
