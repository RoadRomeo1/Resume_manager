package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
  private Integer houseNo;

  @NotBlank(message = "{error.address.details.required}")
  @Size(min = 5, max = 200, message = "{error.address.details.length}")
  @Column(name = "address_details")
  private String addressDetails;

  @Size(max = 100, message = "{error.address.landmark.length}")
  @Column(name = "landmark")
  private String landmark;

  @Size(max = 100, message = "{error.address.otherlandmark.length}")
  @Column(name = "other_landmark")
  private String otherLandmark;

  @NotBlank(message = "{error.address.city.required}")
  @Size(min = 2, max = 50, message = "{error.address.city.length}")
  @Pattern(regexp = "^[a-zA-Z\\s-']+$", message = "{error.address.city.format}")
  @Column(name = "city")
  private String city;

  @NotNull(message = "{error.address.pincode.required}")
  @Pattern(regexp = "^\\d{6}$", message = "{error.address.pincode.invalid}")
  @Column(name = "pin_code")
  private String pinCode;

  @NotBlank(message = "{error.address.country.required}")
  @Size(min = 2, max = 50, message = "{error.address.country.length}")
  @Pattern(regexp = "^[a-zA-Z\\s-']+$", message = "{error.address.country.format}")
  @Column(name = "country")
  private String country;

  public Long getId() {
    return id;
  }

  public Integer getHouseNo() {
    return houseNo;
  }

  public String getAddressDetails() {
    return addressDetails;
  }

  public String getLandmark() {
    return landmark;
  }

  public String getOtherLandmark() {
    return otherLandmark;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getPinCode() {
    return pinCode;
  }
}
