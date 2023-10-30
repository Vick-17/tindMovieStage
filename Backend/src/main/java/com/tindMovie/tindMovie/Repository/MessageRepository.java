package com.tindMovie.tindMovie.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tindMovie.tindMovie.Model.MessageEntity;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    
}
