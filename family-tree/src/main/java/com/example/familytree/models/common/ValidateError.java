package com.example.familytree.models.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ValidateError implements Serializable {
  private static final long serialVersionUID = -317263205014648815L;
  private String code;
  private String errorMessage;
  private boolean isWarning = false;

  public ValidateError(String code, String errorMessage) {
    this.code = code;
    this.errorMessage = errorMessage;
  }
}
