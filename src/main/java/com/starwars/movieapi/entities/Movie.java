/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Oke
 */
@Entity
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(unique = true)
    private String name;
    
    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String openingCrawls;
    
    @Column(columnDefinition = "DATE")
    private LocalDate releaseDate;
    
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MovieCharacter> characters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningCrawls() {
        return openingCrawls;
    }

    public void setOpeningCrawls(String openingCrawls) {
        this.openingCrawls = openingCrawls;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

//    public List<String> getCharacters() {
//        return characters;
//    }
//
//    public void setCharacters(List<String> characters) {
//        this.characters = characters;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.openingCrawls);
        hash = 59 * hash + Objects.hashCode(this.releaseDate);
        hash = 59 * hash + Objects.hashCode(this.comments);
//        hash = 59 * hash + Objects.hashCode(this.characters);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.openingCrawls, other.openingCrawls)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
//        if (!Objects.equals(this.characters, other.characters)) {
//            return false;
//        }
        return true;
    }
    
    


        
}
