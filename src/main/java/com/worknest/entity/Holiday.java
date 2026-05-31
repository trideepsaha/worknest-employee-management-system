package com.worknest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="holiday_name", nullable = false)
    private String holidayName;

    @Column(name="holiday_date", nullable = false, unique = true)
    private LocalDate holidayDate;

    @Column(name="holiday_type")
    private String holidayType;
}
