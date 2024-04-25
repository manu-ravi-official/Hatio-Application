package com.example.todo.application.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(mappedBy = "project")
    private List<Todo> todos = new ArrayList<>();

    @Column(name = "status", columnDefinition = "TINYINT")
    private Status status;

    public enum Status {
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        public final String value;

        Status(String value) {
            this.value = value;
        }
    }
}
