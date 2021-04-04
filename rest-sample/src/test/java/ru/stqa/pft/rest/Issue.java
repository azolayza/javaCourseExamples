package ru.stqa.pft.rest;

import java.util.Objects;

public class Issue {

  private int id;
  private String subject;
  private String description;
  private String state_name;

  public String getStateName() {
    return state_name;
  }

  public Issue withState_name(String state_name) {
    this.state_name = state_name;
    return this;
  }

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public String toString() {
    return "Issue{" +
            "id=" + id +
            ", subject='" + subject + '\'' +
            ", description='" + description + '\'' +
            ", state_name='" + state_name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Issue)) return false;
    Issue issue = (Issue) o;
    return getId() == issue.getId() && Objects.equals(getSubject(), issue.getSubject()) && Objects.equals(getDescription(), issue.getDescription()) && Objects.equals(getStateName(), issue.getStateName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getSubject(), getDescription(), getStateName());
  }
}
