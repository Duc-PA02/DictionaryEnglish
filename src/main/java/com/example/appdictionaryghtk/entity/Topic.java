package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "topic")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", foreignKey = @ForeignKey(name = "fk_topic_creat_user_id"))
    @JsonBackReference
    private User creat_by;

    @Column(name = "creat_at")
    private Timestamp creat_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_by", foreignKey = @ForeignKey(name = "fk_topic_update_user_id"))
    @JsonBackReference
    private User update_by;

    @Column(name = "update_at")
    private Timestamp update_at;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TopicWord> topicWord;
    @PrePersist
    protected void onCreate() {
        creat_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        update_at = new Timestamp(System.currentTimeMillis());
    }

}
