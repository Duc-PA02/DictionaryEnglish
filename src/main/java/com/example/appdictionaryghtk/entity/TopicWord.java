package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "topic_word")
public class TopicWord  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "fk_topicword_topic_id"))
    @JsonBackReference
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_topicword_word_id"))
    @JsonBackReference
    private Words word;
}
