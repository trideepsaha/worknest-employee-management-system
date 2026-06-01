package com.worknest.repository;

import com.worknest.entity.Employee;
import com.worknest.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByEmployeeOrderByCreatedAtDesc(Employee employee);

    List<Notification> findByEmployeeAndIsReadFalseOrderByCreatedAtDesc(Employee employee);
}