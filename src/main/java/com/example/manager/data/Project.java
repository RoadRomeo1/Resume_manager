package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Year;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity class representing project details of a person Contains validation for years and required
 * fields
 */
@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Project {

  // Get current year dynamically
  public static int getCurrentYear() {
    return Year.now().getValue();
  }

  @Id
  @GeneratedValue(
      strategy =
          GenerationType.IDENTITY) // Changed from AUTO to IDENTITY for better H2 compatibility
  @Column(name = "id")
  @Hidden
  private Long id;

  @NotBlank(message = "{error.project.name.required}")
  @Size(min = 3, max = 100, message = "{error.project.name.length}")
  @Column(name = "name")
  private String name;

  // Starting year validation: must be >= 1900 and not in future
  @Column(name = "starting_year")
  @Min(value = 1900, message = "{error.project.year.invalid}")
  @YearNotInFuture(message = "{error.project.year.future}")
  private int startingYear;

  // Ending year validation: must be >= 1900 and not before current year
  @Column(name = "ending_year")
  @Min(value = 1900, message = "{error.project.year.invalid}")
  @YearNotBeforeCurrent(message = "{error.project.year.past}")
  private int endingYear;

  @Column(name = "currently_working")
  private boolean currentlyWorking;

  @NotBlank(message = "{error.project.description.required}")
  @Size(min = 10, max = 1000, message = "{error.project.description.length}")
  @Column(name = "description")
  private String description;

  @Size(max = 200, message = "{error.project.source.length}")
  @Column(name = "source")
  private String source;

  @AssertTrue(message = "{error.project.year.sequence}")
  private boolean isEndingYearValid() {
    return currentlyWorking || endingYear >= startingYear;
  }
}
