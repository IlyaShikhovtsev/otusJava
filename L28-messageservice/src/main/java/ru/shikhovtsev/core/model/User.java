package ru.shikhovtsev.core.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "users")
public class User {
  @Setter(value = AccessLevel.NONE)

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;
  @Column(name = "login", unique = true)
  private String login;

  @ToString.Exclude
  @Column(name = "password")
  private String password;

  @Column(name = "name", unique = true)
  private String name;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "phone_id")
  private Phone phone;

  @Column(name = "role")
  private Role role;

  public User(String login, String password, String name, Address address, Phone phone, Role role) {
    this.login = login;
    this.password = password;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.role = role;
  }
}
