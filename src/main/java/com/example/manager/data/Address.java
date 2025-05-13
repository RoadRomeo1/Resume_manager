package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @Hidden
  private Long id;

  @NotNull(message = "{error.address.houseno.required}")
  @PositiveOrZero(message = "{error.address.houseno.invalid}")
  @Column(name = "house_no")
  private int houseNo;

  @NotBlank(message = "{error.address.details.required}")
  @Column(name = "address_details")
  private String addressDetails;

  @Column(name = "landmark")
  private String landmark;

  @Column(name = "other_landmark")
  private String otherLandmark;

  @NotBlank(message = "{error.address.city.required}")
  @Column(name = "city")
  private String city;

  @NotNull(message = "{error.address.pincode.required}")
  @Pattern(regexp = "^\\d{6}$", message = "{error.address.pincode.invalid}")
  @Column(name = "pin_code")
  private String pinCode;

  @NotBlank(message = "{error.address.country.required}")
  @Column(name = "country")
  private String country;
}
