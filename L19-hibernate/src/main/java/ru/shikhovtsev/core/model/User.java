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
  @Column(name = "name")
  private String name;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "phone_id")
  private Phone phone;

  public User(String name, Address address, Phone phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
  }
}
