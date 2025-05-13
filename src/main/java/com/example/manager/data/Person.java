package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  public Person(
      Long id,
      String firstName,
      String lastName,
      String emailId,
      String phone,
      Set<Address> address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
    this.phone = phone;
    this.address = address;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @Hidden
  private Long id;

  @NotBlank(message = "{error.person.firstname.required}")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "{error.person.lastname.required}")
  @Column(name = "last_name")
  private String lastName;

  @NotBlank(message = "{error.person.email.required}")
  @Column(name = "email")
  @Email(message = "{error.person.email.invalid}")
  private String emailId;

  @NotBlank(message = "{error.person.phone.required}")
  @Pattern(regexp = "^\\d{10}$", message = "{error.person.phone.invalid}")
  @Column(name = "phone")
  private String phone;

  @Valid
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Address> address;

  @Valid
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Project> project;

  @Valid
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Education> education;

  @Valid
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Set<Experience> experience;

  public void setId(Long id) {
    this.id = id;
  }
}
