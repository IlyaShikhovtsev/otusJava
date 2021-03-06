package ru.shikhovtsev.core.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "phones")
public class Phone {
  @Setter(value = AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Id
  private Long id;
  @Column(name = "number")
  private String number;

  public Phone(String number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "Phone{" +
        "number='" + number + '\'' +
        '}';
  }
}
