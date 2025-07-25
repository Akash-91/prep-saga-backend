package com.prep_saga.PrepSaga.service.topic;

import com.prep_saga.PrepSaga.entity.topic.Topic;
import com.prep_saga.PrepSaga.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Optional<Topic> getTopicByTitle(String title) {
        return topicRepository.findByTitle(title);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic updateTopic(Long id, Topic updatedTopic) {
        Topic existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + id));
        existingTopic.setTitle(updatedTopic.getTitle());
        existingTopic.setContent(updatedTopic.getContent());

        return topicRepository.save(existingTopic);
    }

    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + id));

        topicRepository.delete(topic);
    }
}
