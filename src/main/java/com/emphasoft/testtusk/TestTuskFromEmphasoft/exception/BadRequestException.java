

package com.emphasoft.testtusk.TestTuskFromEmphasoft.exception;

public class BadRequestException extends RuntimeException {
  public BadRequestException() {
  }

  public BadRequestException(final String message) {
    super(message);
  }
}
