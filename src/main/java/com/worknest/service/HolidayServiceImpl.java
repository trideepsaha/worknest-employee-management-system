package com.worknest.service;

import com.worknest.dto.HolidayDto;
import com.worknest.entity.Holiday;
import com.worknest.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public Holiday addHoliday(HolidayDto dto) {

        if (holidayRepository.existsByHolidayDate(dto.getHolidayDate())) {
            throw new RuntimeException("Holiday already exists for this date");
        }

        Holiday holiday = Holiday.builder()
                .holidayName(dto.getHolidayName())
                .holidayDate(dto.getHolidayDate())
                .holidayType(dto.getHolidayType())
                .build();

        return holidayRepository.save(holiday);
    }

    @Override
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public List<Holiday> getUpcomingHolidays() {
        return holidayRepository.findByHolidayDateAfterOrderByHolidayDateAsc(LocalDate.now());
    }

    @Override
    public Holiday updateHoliday(Long id, HolidayDto dto) {

        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));

        holiday.setHolidayName(dto.getHolidayName());
        holiday.setHolidayDate(dto.getHolidayDate());
        holiday.setHolidayType(dto.getHolidayType());

        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteHoliday(Long id) {

        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));

        holidayRepository.delete(holiday);
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidayRepository.existsByHolidayDate(date);
    }
}