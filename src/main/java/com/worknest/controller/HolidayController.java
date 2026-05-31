package com.worknest.controller;

import com.worknest.dto.HolidayDto;
import com.worknest.entity.Holiday;
import com.worknest.service.HolidayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @PostMapping
    public Holiday addHoliday(@Valid @RequestBody HolidayDto dto) {
        return holidayService.addHoliday(dto);
    }

    @GetMapping
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    @GetMapping("/upcoming")
    public List<Holiday> getUpcomingHolidays() {
        return holidayService.getUpcomingHolidays();
    }

    @GetMapping("/check")
    public boolean checkHoliday(@RequestParam String date) {
        return holidayService.isHoliday(LocalDate.parse(date));
    }

    @PutMapping("/{id}")
    public Holiday updateHoliday(
            @PathVariable Long id,
            @Valid @RequestBody HolidayDto dto
    ) {
        return holidayService.updateHoliday(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return "Holiday deleted successfully";
    }
}