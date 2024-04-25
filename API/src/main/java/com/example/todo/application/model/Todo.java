package com.example.todo.application.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Getter
@Setter
@Entity
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedDate;

  @ManyToOne
  private Project project;

  @Column(name = "status", columnDefinition = "TINYINT")
  private Status status;

  public enum Status {
    PENDING("PENDING"),
    COMPLETED("COMPLETED");

    public final String value;

    Status(String value) {
      this.value = value;
    }
  }
}
