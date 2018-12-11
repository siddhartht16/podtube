package com.podtube.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "played_status")
public class PlayedStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on", nullable = false)
    @LastModifiedDate
    private Date modifiedOn;

    public PlayedStatus() {}
}
