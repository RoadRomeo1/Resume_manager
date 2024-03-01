package com.example.manager.data;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Person {

  private static final String email_pattern =
      "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
  private static final String phone_pattern = "(^$|[0-9]{10})";

  public Person(
      Long id,
      @NotBlank(message = "First Name can not be empty") String firstName,
      @NotBlank(message = "Last Name can not be empty") String lastName,
      @NotBlank(message = "Email can not be empty")
          @Pattern(
              regexp = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",
              message = "Email should be in proper format")
          String emailId,
      @NotBlank(message = "Phone number can not be empty")
          @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be of  10 digits.")
          String phone,
      Set<Address> address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.phone = phone;
    this.address = address;
  }

  @ApiModelProperty(hidden = true)
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotBlank(message = "First Name can not be empty")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "Last Name can not be empty")
  @Column(name = "last_name")
  private String lastName;

  @NotBlank(message = "Email can not be empty")
  @Pattern(regexp = email_pattern, message = "Email should be in proper format")
  @Column(name = "email")
  private String emailId;

  @NotBlank(message = "Phone number can not be empty")
  @Pattern(regexp = phone_pattern, message = "Phone number should be of  10 digits.")
  @Column(name = "phone")
  private String phone;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Address> address;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Project> project;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Education> education;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Experience> experience;
}
