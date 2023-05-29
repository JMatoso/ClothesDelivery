package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.entities.Product;
import com.clothesdelivery.web.enums.*;
import com.clothesdelivery.web.models.ProductRequest;
import com.clothesdelivery.web.models.Result;
import com.clothesdelivery.web.models.SignUp;
import com.clothesdelivery.web.repositories.IOrderItemRepository;
import com.clothesdelivery.web.repositories.IOrderRepository;
import com.clothesdelivery.web.repositories.IProductRepository;
import com.clothesdelivery.web.repositories.IUserRepository;
import com.clothesdelivery.web.services.FileService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private IProductRepository _products;

    @Autowired
    private IUserRepository _users;

    @Autowired
    private IOrderRepository _orders;

    @GetMapping("/dashboard")
    public String dashboard(@NotNull Model model) {
        var customers = _users.findAllByRole(Role.ROLE_CUSTOMER).size();
        var products = _products.count();

        var orders = _orders.findAll();
        var monthlyOrders = orders.stream().filter(e -> e.getCreatedTime().getMonth() == LocalDate.now().getMonth()).count();
        var pendingOrders = orders.stream().filter(e -> e.getStatus() == OrderStatus.Pendent).count();

        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        model.addAttribute("monthly_orders", monthlyOrders);
        model.addAttribute("pending_orders", pendingOrders);

        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(@NotNull Model model) {
        model.addAttribute("products", _products.findAll());
        return "admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String productEdit(@PathVariable(value = "id", required = false) Long id, @NotNull Model model) {
        var product = new ProductRequest(0L);

        if(id != 0) {
            var result = _products.findById(id);

            if(result.isPresent()) {
                product = result.get().toProductRequest();
            }
        }

        model.addAttribute("productRequest", product);
        return "admin/product-edit";
    }

    @PostMapping("/products/edit")
    public String productEdit(@Valid @ModelAttribute("productRequest") @NotNull ProductRequest productRequest, @NotNull BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("validation", "Fill all required fields");
            return "products/edit";
        }

        if(productRequest.getId() == 0) {
            var product = productRequest.toProductEntity();
            var savedProduct = _products.save(product);

            savedProduct.generateFriendlyUrlFromName();
            savedProduct.generateSKU();
            _products.save(savedProduct);

            model.addAttribute("product", savedProduct);
            return redirect(String.format("admin/products/edit/%d?added", product.getId()));
        }

        var oldProduct = _products.findById(productRequest.getId());

        if(oldProduct.isEmpty()) return redirect("notfound");

        _products.save(oldProduct.get().update(productRequest));
        return redirect(String.format("admin/products/edit/%d?updated", oldProduct.get().getId()));
    }

    private String updateCover(MultipartFile file, @NotNull Category category, String fileName) {
        var path = switch (category) {
            case Tops -> "/images/clothes/tops/";
            case Bottoms -> "/images/clothes/bottoms/";
            case Shoes -> "/images/clothes/shoes/";
            case Accessories -> "/images/clothes/accessories/";
        };

        var fullPath = "";

        try {
            fullPath = FileService.saveFile(file, path, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fullPath;
    }

    @GetMapping("/users/edit/{id}")
    public String usersEdit(@NotNull @PathVariable("id") Long id) {
        return "admin/users-edit";
    }

    @GetMapping("/users")
    public String users(@NotNull Model model) {
        model.addAttribute("users", _users.findAll().stream().filter(e -> e.getRole().equals(Role.ROLE_ADMIN)).toList());
        return "admin/users";
    }

    @GetMapping("/customers")
    public String customers(@NotNull Model model) {
        model.addAttribute("customers", _users.findAll().stream().filter(e -> e.getRole().equals(Role.ROLE_CUSTOMER)).toList());
        return "admin/customers";
    }

    @GetMapping("/products/remove/{bid}")
    public String removeFromBag(@PathVariable @NotNull Long bid) {
        var product = _products.findById(bid);

        if(product.isEmpty()) return notFound;

        _products.delete(product.get());

        return redirect("admin/products?removed");
    }
}
