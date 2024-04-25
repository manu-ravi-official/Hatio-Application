package com.example.todo.application.form.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NotBlank(message = "{username.required}")
@Size(min = 3, max = 20, message = "{username.size}")
@Target({ ANNOTATION_TYPE, FIELD, METHOD, PARAMETER, TYPE_USE })
@Retention(RUNTIME) // Specifies that the annotation should be available at runtime
@Constraint(validatedBy = {}) // Specifies the validator class for this constraint
@Documented
public @interface Username {
  /**
   * Default validation error message. This message will be used if the validation
   * fails. The placeholder {jakarta.validation.constraints.Pattern.message} will be
   * replaced with the actual error message defined in the validation framework.
   */
  String message() default "{jakarta.validation.constraints.Pattern.message}";

  /**
   * Specifies the validation groups to which this constraint belongs. Useful for
   * categorizing constraints and applying them selectively.
   */
  Class<?>[] groups() default {};

  /**
   * Custom payload that can provide additional information about the validation
   * error. Typically used for advanced error handling and reporting.
   */
  Class<? extends Payload>[] payload() default {};
}
