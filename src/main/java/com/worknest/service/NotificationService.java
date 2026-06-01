package com.worknest.service;

import com.worknest.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Long employeeId, String message);

    List<Notification> getEmployeeNotifications(Long employeeId);

    List<Notification> getUnreadNotifications(Long employeeId);

    Notification markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);
}