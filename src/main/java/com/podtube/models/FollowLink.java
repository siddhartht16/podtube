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
@Table(name = "follow_link")
public class FollowLink {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "follower_id")
    @ManyToOne User follower;

    @JoinColumn(name = "followee_id")
    @ManyToOne User followee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on", nullable = false)
    @LastModifiedDate
    private Date modifiedOn;
}
