package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Experience {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @Hidden
  private Long id;

  @NotBlank(message = "Designation can not be blank.")
  @Column(name = "designation")
  private String designation;

  @NotBlank(message = "Company name can not be blank.")
  @Column(name = "company")
  private String company;

  @Column(name = "starting_year")
  private int startingYear;

  @Column(name = "ending_year")
  private int endingYear;

  @Column(name = "currently_working")
  private boolean currentlyWorking;

  @NotBlank(message = "Description can not be empty.")
  @Column(name = "description")
  private String description;
}
