package com.javanc.service;

import org.springframework.stereotype.Service;

import java.util.List;


public interface DashboardService {
    Long findCountUser();
    Long findCountOrders();
    Long findCountProducts();
    List<Object[]> findTop5Recent();
    List<Object[]> findTotal12Months();
    List<Object[]> findTop5Orders();
}
