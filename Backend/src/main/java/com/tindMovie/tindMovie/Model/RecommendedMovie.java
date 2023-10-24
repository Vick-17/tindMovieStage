package com.tindMovie.tindMovie.Model;

public class RecommendedMovie {

  private MovieEntity movie;
  private int matchScore;

  public RecommendedMovie(MovieEntity movie, int matchScore) {
    this.movie = movie;
    this.matchScore = matchScore;
  }

  public MovieEntity getMovie(){
    return movie;
  }

  public int getMatchScore() {
    return matchScore;
  }
}
