package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;

import java.util.List;
import java.util.Optional;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
        /**
     * Find an account by username.
     * @param postedBy the username of the account
     * @return Optional containing the account if found, otherwise empty
     */
    List<Message> findByPostedBy(int postedBy);
}
