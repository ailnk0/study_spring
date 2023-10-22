package com.helloshop.service;

public class JoinInDuplicateException extends RuntimeException {

  public JoinInDuplicateException(String email) {
    super(String.format(
        "Your email '%s' has already been joined. Please use a different email.", email));
  }
}
