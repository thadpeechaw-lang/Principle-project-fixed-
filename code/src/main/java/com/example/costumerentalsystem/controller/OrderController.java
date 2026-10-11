package com.example.costumerentalsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.costumerentalsystem.domain.entity.User;
import com.example.costumerentalsystem.domain.enums.Role;
import com.example.costumerentalsystem.dto.response.RentalResponse;
import com.example.costumerentalsystem.service.RentalService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    private final RentalService rentalService;

    public OrderController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // /user/rentals เป็นลิงก์ในเมนูที่มีอยู่แล้ว เลยให้ชี้มาหน้าเดียวกับ /orders
    @GetMapping({"/orders", "/user/rentals"})
    public String myOrders(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", loggedInUser.getUsername());

        // ยังไม่มีปุ่มเปลี่ยนหน้า เอา 50 รายการล่าสุดไปก่อน
        Pageable pageable = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<RentalResponse> orders = (loggedInUser.getRole() == Role.ADMIN)
                ? rentalService.findAll(null, pageable)
                : rentalService.findByUser(loggedInUser.getId(), pageable);

        model.addAttribute("orders", orders.getContent());
        return "orders";
    }
}
