package com.worknest.repository;

import com.worknest.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    Optional<Holiday> findByHolidayDate(LocalDate holidayDate);

    boolean existsByHolidayDate(LocalDate holidayDate);

    List<Holiday> findByHolidayDateAfterOrderByHolidayDateAsc(LocalDate date);
}