package com.helloshop.repository;

import java.util.Objects;

/**
 * Represents a member of the HelloShop application.
 */
public class Member {

  private Long id;
  private String email;

  /**
   * Creates a new member with the given email address.
   *
   * @param email the email address of the member
   */
  public Member(String email) {
    this.email = email;
  }

  /**
   * Creates a new member with the given ID and email address.
   *
   * @param id    the ID of the member
   * @param email the email address of the member
   */
  public Member(Long id, String email) {
    this.id = id;
    this.email = email;
  }

  /**
   * Returns the ID of the member.
   *
   * @return the ID of the member
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the member.
   *
   * @param id the ID of the member
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the email address of the member.
   *
   * @return the email address of the member
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email address of the member.
   *
   * @param email the email address of the member
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the object to compare to
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Member other)) {
      return false;
    }
    return Objects.equals(this.id, other.id) && Objects.equals(this.email, other.email);
  }
}