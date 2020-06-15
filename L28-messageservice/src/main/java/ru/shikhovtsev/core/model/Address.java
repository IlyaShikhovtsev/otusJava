package ru.shikhovtsev.core.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "addresses")
public class Address {
  @Setter(value = AccessLevel.NONE)
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "street")
  private String street;

  public Address(String street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return "Address{" +
        "street='" + street + '\'' +
        '}';
  }
}
