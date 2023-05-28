package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.entities.Order;
import com.clothesdelivery.web.entities.OrderItem;
import com.clothesdelivery.web.enums.OrderStatus;
import com.clothesdelivery.web.extensions.Extensions;
import com.clothesdelivery.web.repositories.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class OrdersController extends BaseController {
    @Autowired
    private IOrderRepository _orders;

    @Autowired
    private IProductRepository _products;

    @Autowired
    private IOrderItemRepository _ordersItems;

    @Autowired
    private ICartRepository _shoppingCarts;

    @Autowired
    private IAddressRepository _addresses;

    @Value("${clothes.shipping.value}")
    private Long shippingValue;

    @Value("${clothes.free.shipping.value}")
    private BigDecimal shoppingValueForFreeShipping;

    @GetMapping("/order/{orderReference}")
    public String detail(@PathVariable @NotNull String orderReference, @NotNull Model model) {
        return "order";
    }

    @GetMapping("/checkout")
    public String checkout(@NotNull Model model) {
        var user = getAuthenticatedUser();

        if(user == null) return login;

        var cartItems = _shoppingCarts.findByUserId(user.getId());

        if(cartItems.size() == 0) return redirect("bag");

        var value = BigDecimal.ZERO;

        for (var item : cartItems) {
            value = value.add(item.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        model.addAttribute("totalProducts", Extensions.toFormattedCurrency(value));

        if(value.compareTo(shoppingValueForFreeShipping) < 0) {
            value = value.add(BigDecimal.valueOf(shippingValue));
            model.addAttribute("ship", Extensions.toFormattedCurrency(BigDecimal.valueOf(shippingValue)));
        } else {
            model.addAttribute("ship", "Free");
        }

        var address = _addresses.findById(user.getAddressId());

        model.addAttribute("authenticatedUser", user);
        model.addAttribute("products", cartItems);
        model.addAttribute("address", address.orElse(null));
        model.addAttribute("total", Extensions.toFormattedCurrency(value));
        model.addAttribute("freeShipping", Extensions.toFormattedCurrency(shoppingValueForFreeShipping));

        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkout(
            @RequestParam(value = "paypalEmail") String paypalEmail,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "note", required = false) String note) {

        var user = getAuthenticatedUser();
        if(user == null) return login;

        var cartItems = _shoppingCarts.findByUserId(user.getId());
        if(cartItems.size() == 0) return redirect("bag");

        int items = 0;
        var value = BigDecimal.ZERO;
        var orderItems = new ArrayList<OrderItem>();

        for (var item : cartItems) {
            items += item.getQuantity();
            value = value.add(item.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        boolean hasShipping = false;


        if(value.compareTo(shoppingValueForFreeShipping) < 0) {
            hasShipping = true;
            value = value.add(BigDecimal.valueOf(shippingValue));
        }

        var order = new Order();
        order.generateOrderReference();
        order.setUserId(user.getId());
        order.setUserAddressId(user.getAddressId());
        order.setReferenceLocation(location);
        order.setNote(note);
        order.setPayPalEmail(paypalEmail);
        order.setPaid(true);
        order.setStatus(OrderStatus.Pendent);
        order.setTotal(value);
        order.setCreatedTime(LocalDateTime.now());
        order.setShipping(hasShipping ? BigDecimal.valueOf(shippingValue) : BigDecimal.ZERO);
        order.setItems(items);

        var savedOrder = _orders.save(order);

        for (var item: cartItems) {
            var orderItem = new OrderItem();

            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductPrice(item.getProductPrice());
            orderItem.setProductImage(item.getProductImage());
            orderItem.setProductQuantity(item.getQuantity());
            orderItem.setProductName(item.getProductName());
            orderItem.setProductId(item.getProductId());
            orderItems.add(orderItem);

            var product = _products.findById(item.getProductId());

            if(product.isPresent()) {
                if(product.get().getQuantityInStock() >= item.getQuantity()) {
                    product.get().setQuantityInStock(product.get().getQuantityInStock() - item.getQuantity());
                    _products.save(product.get());
                    _shoppingCarts.delete(item);
                }
            }
        }

        _ordersItems.saveAll(orderItems);

        return redirect("complete?success");
    }

    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }
}
