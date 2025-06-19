package com.example.manager.data;

import com.example.manager.data.constants.EducationType;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity class representing education details of a person Contains validation for years and
 * required fields
 */
@Entity
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Education {

  @Id
  @GeneratedValue(
      strategy =
          GenerationType.IDENTITY) // Changed from AUTO to IDENTITY for better H2 compatibility
  @Column(name = "id")
  @Hidden
  private Long id;

  @NotBlank(message = "{error.education.name.required}")
  @Size(min = 3, max = 100, message = "{error.education.name.length}")
  @Column(name = "name")
  private String name;

  @NotNull(message = "{error.education.type.required}")
  @Column(name = "type")
  private EducationType type;

  @NotBlank(message = "{error.education.university.required}")
  @Size(min = 3, max = 100, message = "{error.education.university.length}")
  @Column(name = "university")
  private String university;

  // Starting year validation: must be >= 1900 and not in future
  @Column(name = "starting_year")
  @Min(value = 1900, message = "{error.education.year.invalid}")
  @YearNotInFuture(message = "{error.education.year.future}")
  private int startingYear;

  // Ending year validation: must be >= 1900 and not before current year
  @Column(name = "ending_year")
  @Min(value = 1900, message = "{error.education.year.invalid}")
  @YearNotBeforeCurrent(message = "{error.education.year.past}")
  private int endingYear;

  @Column(name = "percentage", precision = 5, scale = 2)
  @DecimalMin(value = "0.0", message = "{error.education.percentage.invalid}")
  @DecimalMax(value = "100.0", message = "{error.education.percentage.invalid}")
  @Digits(integer = 3, fraction = 2, message = "{error.education.percentage.format}")
  private BigDecimal percentage;
}
