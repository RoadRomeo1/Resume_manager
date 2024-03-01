package com.example.manager.data;

import com.example.manager.data.constants.EducationType;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Education {

  @ApiModelProperty(hidden = true)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotBlank(message = "Education name must not be empty")
  @Column(name = "name")
  private String name;

  @NotBlank(message = "Education type must not be empty")
  @Column(name = "type")
  private EducationType type;

  @NotBlank(message = "University name must not be empty")
  @Column(name = "university")
  private String university;

  @Column(name = "starting_year")
  private int startingYear;

  @Column(name = "ending_year")
  private int endingYear;

  @Column(name = "percentage")
  private double percentage;
}
