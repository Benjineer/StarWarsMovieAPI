/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.starwars.movieapi.dtos;

import java.time.LocalDate;

/**
 *
 * @author Oke
 */
public class MovieDTO {
    
    private String name;
    
    private String openingCrawls;
    
    private String releaseDate;
    
    private int commentCount;

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
