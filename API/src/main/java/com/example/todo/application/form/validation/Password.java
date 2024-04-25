package com.example.todo.application.form.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@NotBlank(message = "{password.required}")
@Size(message = "{password.size}", min = 8, max = 24)
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()+\\-=._]).+$", message = "{password.should.contain}")
@Target({ ANNOTATION_TYPE, FIELD, METHOD, PARAMETER, TYPE_USE })
@Retention(RUNTIME) // Specifies that the annotation should be available at runtime
@Constraint(validatedBy = {}) // Specifies the validator class for this constraint
@Documented
public @interface Password {
  /**
   * Default error message when validation fails.
   *
   * @return The error message.
   */
  String message() default "{jakarta.validation.constraints.Pattern.message}";

  /**
   * Groups the constraint belongs to.
   *
   * @return The groups.
   */
  Class<?>[] groups() default {};

  /**
   * Payload associated with the constraint.
   *
   * @return The payload.
   */
  Class<? extends Payload>[] payload() default {};
}
