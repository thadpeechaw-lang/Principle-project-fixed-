package com.example.costumerentalsystem.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.costumerentalsystem.domain.entity.User;
import com.example.costumerentalsystem.dto.request.RentalCreateRequest;
import com.example.costumerentalsystem.exception.BadRequestException;
import com.example.costumerentalsystem.exception.ConflictException;
import com.example.costumerentalsystem.exception.ResourceNotFoundException;
import com.example.costumerentalsystem.service.CostumeQueryService;
import com.example.costumerentalsystem.service.RentalService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final CostumeQueryService costumeQueryService;

    public RentalController(RentalService rentalService, CostumeQueryService costumeQueryService) {
        this.rentalService = rentalService;
        this.costumeQueryService = costumeQueryService;
    }

    @GetMapping("/new/{costumeId}")
    public String showRentalForm(@PathVariable Long costumeId, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        try {
            model.addAttribute("costume", costumeQueryService.getById(costumeId));
        } catch (ResourceNotFoundException e) {
            return "redirect:/";
        }
        return "user/rental-form";
    }

    @PostMapping("/save")
    public String createRental(@RequestParam Long costumeId,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        try {
            rentalService.create(loggedInUser.getId(), new RentalCreateRequest(costumeId, startDate, endDate));
        } catch (ConflictException | BadRequestException | ResourceNotFoundException e) {
            // เช่น ชุดถูกจองแล้ว หรือวันคืนก่อนวันเริ่ม แสดงข้อความในหน้าฟอร์มแทนที่จะเป็นหน้า error
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/rentals/new/" + costumeId;
        }
        return "redirect:/orders";
    }
}
