package com.example.manager.data;

import com.example.manager.data.constants.EducationType;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
  @Column(name = "name")
  private String name;

  // Removed @NotBlank annotation as it's not valid for enum types
  // @NotBlank can only be used with String types, not with enums
  @Column(name = "type")
  private EducationType type;

  @NotBlank(message = "{error.education.university.required}")
  @Column(name = "university")
  private String university;

  // Added year validation to ensure years are between 1900 and current year
  @Column(name = "starting_year")
  @Min(value = 1900, message = "{error.education.year.invalid}")
  @YearNotInFuture(message = "{error.education.year.invalid}")
  private int startingYear;

  @Column(name = "ending_year")
  @Min(value = 1900, message = "{error.education.year.invalid}")
  @YearNotInFuture(message = "{error.education.year.invalid}")
  private int endingYear;

  @Column(name = "percentage")
  @Min(value = 0, message = "{error.education.percentage.invalid}")
  @Max(value = 100, message = "{error.education.percentage.invalid}")
  private double percentage;
}
