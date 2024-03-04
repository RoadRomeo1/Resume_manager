package com.example.manager.data;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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

  @NotBlank(message = "First Name can not be empty")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "Last Name can not be empty")
  @Column(name = "last_name")
  private String lastName;

  @NotBlank(message = "Email can not be empty")
  @Column(name = "email")
  @Email
  private String emailId;

  @NotBlank(message = "Phone number can not be empty")
  @Length(max = 10, min = 10, message = "Contact should be of 10 digits")
  @Pattern(regexp = "^\\d{10}$")
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
