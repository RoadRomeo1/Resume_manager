package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity class representing work experience details of a person Contains validation for years and
 * required fields
 */
@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Experience {

  @Id
  @GeneratedValue(
      strategy =
          GenerationType.IDENTITY) // Changed from AUTO to IDENTITY for better H2 compatibility
  @Column(name = "id")
  @Hidden
  private Long id;

  @NotBlank(message = "{error.experience.designation.required}")
  @Size(min = 3, max = 100, message = "{error.experience.designation.length}")
  @Column(name = "designation")
  private String designation;

  @NotBlank(message = "{error.experience.company.required}")
  @Size(min = 2, max = 100, message = "{error.experience.company.length}")
  @Column(name = "company")
  private String company;

  // Starting year validation: must be >= 1900 and not in future
  @Column(name = "starting_year")
  @Min(value = 1900, message = "{error.experience.year.invalid}")
  @YearNotInFuture(message = "{error.experience.year.future}")
  private int startingYear;

  // Ending year validation: must be >= 1900 and not before current year
  @Column(name = "ending_year")
  @Min(value = 1900, message = "{error.experience.year.invalid}")
  @YearNotBeforeCurrent(message = "{error.experience.year.past}")
  private int endingYear;

  @Column(name = "currently_working")
  private boolean currentlyWorking;

  @NotBlank(message = "{error.experience.description.required}")
  @Size(min = 10, max = 1000, message = "{error.experience.description.length}")
  @Column(name = "description")
  private String description;
}
