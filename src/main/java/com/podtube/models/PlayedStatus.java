package com.podtube.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

public class PlayedStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    PlayedStatus(){}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on", nullable = false)
    @LastModifiedDate
    private Date modifiedOn;

    @OneToOne
//	@JsonIgnore
    private User user;

    @OneToOne
//	@JsonIgnore
    private Podcast podcast;

    @OneToOne
//	@JsonIgnore
    private Episode episode;
}
