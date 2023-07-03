package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.entities.Product;
import com.clothesdelivery.web.enums.*;
import com.clothesdelivery.web.extensions.Functions;
import com.clothesdelivery.web.models.ProductRequest;
import com.clothesdelivery.web.models.Result;
import com.clothesdelivery.web.models.SignUp;
import com.clothesdelivery.web.repositories.*;
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
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Autowired
    private IProductRepository _products;

    @Autowired
    private IUserRepository _users;

    @Autowired
    private IOrderRepository _orders;

    @Autowired
    private IAddressRepository _addresses;

    @Autowired
    private IOrderItemRepository _ordersItems;

    @GetMapping("/dashboard")
    public String dashboard(@NotNull Model model) {
        var customers = _users.findAllByRole(Role.ROLE_CUSTOMER).size();
        var products = _products.count();

        var orders = _orders.findAll();
        var doneOrders = orders.stream().filter(e -> e.getStatus() == OrderStatus.Done).count();
        var sentOrders = orders.stream().filter(e -> e.getStatus() == OrderStatus.Sent).count();
        var pendingOrders = orders.stream().filter(e -> e.getStatus() == OrderStatus.Pendent).count();
        var processedOrders = orders.stream().filter(e -> e.getStatus() == OrderStatus.Processed).count();
        var preparingOrders = orders.stream().filter(e -> e.getStatus() == OrderStatus.Processing).count();
        var monthlyOrders = orders.stream().filter(e -> e.getCreatedTime().getMonth() == LocalDate.now().getMonth()).count();

        long ordersSize = orders.size();

        model.addAttribute("products", products);
        model.addAttribute("customers", customers);
        model.addAttribute("monthly_orders", monthlyOrders);
        model.addAttribute("pending_orders", pendingOrders);
        model.addAttribute("sent_orders", Functions.calculatePercentage(sentOrders, ordersSize, true));
        model.addAttribute("done_orders", Functions.calculatePercentage(doneOrders, ordersSize, true));
        model.addAttribute("preparing_orders", Functions.calculatePercentage(pendingOrders, ordersSize, true));
        model.addAttribute("processed_orders", Functions.calculatePercentage(processedOrders, ordersSize, true));
        model.addAttribute("processing_orders", Functions.calculatePercentage(preparingOrders, ordersSize, true));

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

    @GetMapping("/orders")
    public String orders(@NotNull Model model) {
        model.addAttribute("orders", _orders.findAll());
        return "admin/orders";
    }

    @GetMapping("/order/{orderReference}")
    public String order(@PathVariable @NotNull String orderReference, @NotNull Model model) {
        var user = getAuthenticatedUser();
        if(user == null) return login;

        var order = _orders.findByOrderReference(orderReference);

        if(order == null) return redirect("notfound");

        var address = _addresses.findById(order.getUserAddressId());
        var orderItems = _ordersItems.findAllByOrderId(order.getId());

        model.addAttribute("order", order);
        model.addAttribute("order_items", orderItems);
        model.addAttribute("address", address.orElse(null));
        model.addAttribute("shipping", order.getShipping().compareTo(BigDecimal.ZERO) > 0 ? order.getShipping() : "Free");

        return "admin/order";
    }

    @PostMapping("/order")
    public String changeStatus(@RequestParam(value = "status") OrderStatus status, @RequestParam(value = "ref") @NotNull String ref) {
        if(ref.isEmpty()) {
            return redirect("admin/orders");
        }

        var order = _orders.findByOrderReference(ref);

        if(order == null) {
            return redirect("admin/orders");
        }

        if(order.getStatus() != status) {
            order.setStatus(status);
            _orders.save(order);
            return redirect(String.format("admin/order/%s?changed", ref));
        }

        return redirect(String.format("admin/order/%s?unchanged", ref));
    }

    @GetMapping("/products/remove/{bid}")
    public String removeProduct(@PathVariable @NotNull Long bid) {
        var product = _products.findById(bid);

        if(product.isEmpty()) return notFound;

        _products.delete(product.get());

        return redirect("admin/products?removed");
    }

    @GetMapping("/users/remove/{uid}")
    public String removeUser(@PathVariable @NotNull Long uid) {
        var user = _users.findById(uid);

        if(user.isEmpty()) return notFound;

        _users.delete(user.get());

        return user.get().getRole() == Role.ROLE_CUSTOMER ?
                redirect("admin/customers?removed") :
                redirect("admin/users?removed");
    }
}
