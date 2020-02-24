package org.nds.dhp.bus.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResolvedEnvironmentVariableValidator implements
    ConstraintValidator<ResolvedEnvironmentVariable, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String trimmedValue = value.trim();
    return !(trimmedValue.startsWith("${") && trimmedValue.endsWith("}"));
  }
}
