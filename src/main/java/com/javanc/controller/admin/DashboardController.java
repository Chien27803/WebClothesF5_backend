package com.javanc.controller.admin;

import com.javanc.model.response.admin.Top5OrderResponse;
import com.javanc.model.response.admin.Top5RecentResponse;
import com.javanc.model.response.admin.TotalMonthResponse;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

public class DashboardController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DashboardService dashboardService;

    @GetMapping("/api/admin/user")
    public ResponseEntity<?> thongkeUser(){
        Long numberUser = dashboardService.findCountUser();
        return ResponseEntity.ok()
                .body(numberUser);
    }
    @GetMapping("/api/admin/orders")
    public ResponseEntity<?> thongkeOrders(){
        Long numberOrders = dashboardService.findCountOrders();
        return ResponseEntity.ok()
                .body(numberOrders);
    }
    @GetMapping("/api/admin/product")
    public ResponseEntity<?> thongkeProduct(){
        Long numberProduct = dashboardService.findCountProducts();
        return ResponseEntity.ok()
                .body(numberProduct);
    }
    @GetMapping("api/admin/recent")
    public ResponseEntity<?> thongkeRecent() {
        List<Object[]> listTop5 = dashboardService.findTop5Recent();
        List<Top5RecentResponse> list = listTop5.stream().map(obj ->{
            String src = (String) obj[0];
            String name = (String) obj[1];
            String email = (String) obj[2];
            Number price = (Number) obj[3];
            Long totalPrice = price.longValue();
            return new Top5RecentResponse(src,name,email,totalPrice);


        }).toList();


        return ResponseEntity.ok(list);
    }
    @GetMapping("/api/admin/total_month")
    public ResponseEntity<?> thongkeTotalMonth() {
        List<Object[]> raw = dashboardService.findTotal12Months();

        // Đưa dữ liệu từ DB vào map: month -> totalMonth
        Map<Integer, Long> dataMap = raw.stream()
                .collect(Collectors.toMap(
                        obj -> ((Number) obj[0]).intValue(),
                        obj -> ((Number) obj[1]).longValue()
                ));

        // Tạo danh sách đủ 12 tháng (tháng nào thiếu thì gán 0)
        List<TotalMonthResponse> result = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            result.add(new TotalMonthResponse(i, dataMap.getOrDefault(i, 0L)));
        }

        return ResponseEntity.ok(result);
    }
    @GetMapping("/api/admin/topOrder")
    public ResponseEntity<?> thongkeTopOrder() {
        List<Object[]> topOrder = dashboardService.findTop5Orders();
        List<Top5OrderResponse> list = topOrder.stream().map(obj -> {
            Long product_id = ((Number) obj[0]).longValue();
            String src = (String) obj[1];
            String name = (String) obj[2];
            String description = (String) obj[3];
            Number priceNumber = (Number) obj[4];
            Long price = priceNumber.longValue();
            Long soul_count = ((Number) obj[5]).longValue();

            return new Top5OrderResponse(product_id, src, name, description, price, soul_count);
        }).toList();
        return ResponseEntity.ok(list);
    }
}
