package com.worknest.controller;

import com.worknest.entity.Notification;
import com.worknest.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/employee/{employeeId}")
    public Notification createNotification(@PathVariable Long employeeId, @RequestParam String message){
        return notificationService.createNotification(employeeId, message);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Notification> getEmployeeNotifications(@PathVariable Long employeeId) {
        return notificationService.getEmployeeNotifications(employeeId);
    }

    @GetMapping("/employee/{employeeId}/unread")
    public List<Notification> getUnreadNotifications(@PathVariable Long employeeId) {
        return notificationService.getUnreadNotifications(employeeId);
    }

    @PutMapping("/{notificationId}/read")
    public Notification markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    @DeleteMapping("/{notificationId}")
    public String deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return "Notification deleted successfully";
    }
}