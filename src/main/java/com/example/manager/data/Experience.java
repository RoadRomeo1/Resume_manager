package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
  @Column(name = "designation")
  private String designation;

  @NotBlank(message = "{error.experience.company.required}")
  @Column(name = "company")
  private String company;

  // Added year validation to ensure years are between 1900 and current year
  @Column(name = "starting_year")
  @Min(value = 1900, message = "{error.experience.year.invalid}")
  @YearNotInFuture(message = "{error.experience.year.invalid}")
  private int startingYear;

  @Column(name = "ending_year")
  @Min(value = 1900, message = "{error.experience.year.invalid}")
  @YearNotInFuture(message = "{error.experience.year.invalid}")
  private int endingYear;

  @Column(name = "currently_working")
  private boolean currentlyWorking;

  @NotBlank(message = "{error.experience.description.required}")
  @Column(name = "description")
  private String description;
}
