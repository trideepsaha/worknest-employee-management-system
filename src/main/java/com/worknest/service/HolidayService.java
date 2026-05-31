package com.worknest.service;

import com.worknest.dto.HolidayDto;
import com.worknest.entity.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {

    Holiday addHoliday(HolidayDto dto);

    List<Holiday> getAllHolidays();

    List<Holiday> getUpcomingHolidays();

    Holiday updateHoliday(Long id, HolidayDto dto);

    void deleteHoliday(Long id);

    boolean isHoliday(LocalDate date);
}