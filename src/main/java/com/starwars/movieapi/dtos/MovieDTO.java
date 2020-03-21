/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 *
 * @author Oke
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MovieDTO {
    
    public static void main(String[] args) {
        
    }
    
    private Long id;
    
    private String name;
    
    private String openingCrawls;
    
    private String releaseDate;
    
    private int commentCount;

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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
    
    
    
}
