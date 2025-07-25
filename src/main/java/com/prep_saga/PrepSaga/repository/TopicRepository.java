package com.prep_saga.PrepSaga.repository;

import com.prep_saga.PrepSaga.entity.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTitle(String title);
}
