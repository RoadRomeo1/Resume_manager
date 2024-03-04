package com.example.manager.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @JsonIgnore
  private Long id;

  @NotNull(message = "House number can not be empty.")
  @Positive(message = "Please enter valid house number.")
  @Column(name = "house_no")
  private int houseNo;

  @NotBlank(message = "Address detail can not be empty")
  @Column(name = "address_details")
  private String addressDetails;

  @Column(name = "landmark")
  private String landmark;

  @Column(name = "other_landmark")
  private String otherLandmark;

  @NotBlank(message = "City name can not be empty")
  @Column(name = "city")
  private String city;

  @NotNull(message = "Pin code can not be empty")
  @Column(name = "pin_code")
  private long pinCode;

  @NotBlank(message = "Country name can not be empty")
  @Column(name = "country")
  private String country;
}
