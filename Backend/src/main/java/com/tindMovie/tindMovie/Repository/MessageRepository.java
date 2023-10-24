package com.tindMovie.tindMovie.Repository;

import org.springframework.data.repository.CrudRepository;

import com.tindMovie.tindMovie.Model.MessageEntity;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    
}
