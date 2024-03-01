package com.example.manager.data;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {

  @ApiModelProperty(hidden = true)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull(message = "House number can not be empty.")
  @Positive(message = "Please enter valid house number.")
  @Column(name = "house_no")
  private int house_no;

  @NotBlank(message = "Address detail can not be empty")
  @Column(name = "address_details")
  private String address_details;

  @Column(name = "landmark")
  private String landmark;

  @Column(name = "other_landmark")
  private String other_landmark;

  @NotBlank(message = "City name can not be empty")
  @Column(name = "city")
  private String city;

  @NotNull(message = "Pin code can not be empty")
  @Column(name = "pin_code")
  private long pin_code;

  @NotBlank(message = "Country name can not be empty")
  @Column(name = "country")
  private String country;
}
