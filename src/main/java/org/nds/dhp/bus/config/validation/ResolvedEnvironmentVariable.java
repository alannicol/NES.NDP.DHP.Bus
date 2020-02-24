package org.nds.dhp.bus.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
  Due to a bug in Spring Boot (https://github.com/spring-projects/spring-boot/issues/18816),
  unresolved properties placeholders (environment variables in this case) cannot be validated with
  JSR-303 javax.validation constraint annotations such as @NotNull or @NotBlank.
  As a workaround creating custom annotation and validator.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ResolvedEnvironmentVariableValidator.class)
public @interface ResolvedEnvironmentVariable {
  String message() default "{unresolved environment variable placeholder}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
