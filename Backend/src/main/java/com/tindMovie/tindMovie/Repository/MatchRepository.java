package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.MatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<MatchEntity, Long> {
}
