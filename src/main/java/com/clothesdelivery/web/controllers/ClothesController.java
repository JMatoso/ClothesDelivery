package com.clothesdelivery.web.controllers;

import com.clothesdelivery.web.entities.ShoppingCart;
import com.clothesdelivery.web.enums.Category;
import com.clothesdelivery.web.enums.ClothesSize;
import com.clothesdelivery.web.enums.GenreStyle;
import com.clothesdelivery.web.enums.ProductFilters;
import com.clothesdelivery.web.extensions.Extensions;
import com.clothesdelivery.web.repositories.ICartRepository;
import com.clothesdelivery.web.repositories.IProductRepository;
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

@Controller
public class ClothesController extends BaseController {
    @Autowired
    private IProductRepository _products;

    @Autowired
    private ICartRepository _shoppingCarts;

    @Value("${clothes.shipping.value}")
    private Long shippingValue;

    @Value("${clothes.free.shipping.value}")
    private BigDecimal shoppingValueForFreeShipping;

    @GetMapping("/")
    public String index(@NotNull Model model) {
        var products = _products.findAll();

        var bestProducts = products.stream().filter(e -> e.getFilter() == ProductFilters.Best).limit(12);
        var newestProducts = products.stream().filter(e -> e.getFilter() == ProductFilters.New).limit(12);
        var hottestProducts = products.stream().filter(e -> e.getFilter() == ProductFilters.Hot).limit(12);

        model.addAttribute("best_products", bestProducts);
        model.addAttribute("newest_products", newestProducts);
        model.addAttribute("hottest_products", hottestProducts);

        return "index";
    }

    @GetMapping("/products")
    public String products(
            @RequestParam(value = "category", required = false) Category category,
            @RequestParam(value = "style", required = false) GenreStyle style,
            @RequestParam(value = "size", required = false) ClothesSize size,
            @RequestParam(value = "search", required = false) String search,
            @NotNull Model model) {

        var products = _products.findAll();

        if(search != null) {
            products = products.stream().filter(e ->
                    e.getName().toLowerCase().contains(search.toLowerCase()) ||
                    e.getBrand().toLowerCase().contains(search.toLowerCase()) ||
                    e.getColor().toLowerCase().contains(search.toLowerCase()) ||
                    e.getDescription().toLowerCase().contains(search.toLowerCase()) ||
                    e.getPrice().toString().toLowerCase().contains(search.toLowerCase()) ||
                    e.getSku().toLowerCase().contains(search.toLowerCase())).toList();
        }

        if(category != null) {
            products = products.stream()
                    .filter(e -> e.getCategory() == category)
                    .toList();
        }

        if(style != null) {
            products = products.stream()
                    .filter(e -> e.getGenreStyle() == style)
                    .toList();
        }

        if(size != null) {
            products = products.stream()
                    .filter(e -> e.getSize() == size)
                    .toList();
        }

        model.addAttribute("products", products);

        return "products";
    }

    @GetMapping("/detail/{friendlyUrl}")
    public String detail(@PathVariable @NotNull String friendlyUrl, @NotNull Model model) {
        if(friendlyUrl.isEmpty()) return redirect("products");

        var product = _products.findByFriendlyUrl(friendlyUrl);

        if(product == null) return notFound;

        var recommendedProducts = _products.findByCategoryOrGenreStyleOrBrand(product.getCategory(), product.getGenreStyle(), product.getBrand()).stream().limit(8);

        model.addAttribute("product", product);
        model.addAttribute("recommended_product", recommendedProducts);

        return "detail";
    }

    @PostMapping("/detail")
    public String addToCart(@RequestParam(value = "quantity") int quantity, @RequestParam(value = "friendlyUrl") String friendlyUrl) {
        var returnUrl = String.format("redirect:/detail/%s", friendlyUrl);

        var user = getAuthenticatedUser();

        if(user == null) return returnUrl + "?unauthorized";

        var product = _products.findByFriendlyUrl(friendlyUrl);

        if(product == null) return notFound;

        if(product.getQuantityInStock() <= 0) return returnUrl + "?out";

        if (quantity < 1 || quantity > product.getQuantityInStock()) return returnUrl + "?invalid";

        var shoppingCart = new ShoppingCart();
        shoppingCart.setProductId(product.getId());
        shoppingCart.setAddedDate(LocalDateTime.now());
        shoppingCart.setProductName(product.getName());
        shoppingCart.setProductImage(product.getImageUrl());
        shoppingCart.setProductPrice(product.getPrice());
        shoppingCart.setQuantity(quantity);
        shoppingCart.setUserId(user.getId());

        _shoppingCarts.save(shoppingCart);

        return returnUrl + "?success";
    }

    @GetMapping("/bag")
    public String bag(@NotNull Model model){
        var user = getAuthenticatedUser();

        if(user == null) return login;

        var cartItems = _shoppingCarts.findByUserId(user.getId());

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

        model.addAttribute("products", cartItems);
        model.addAttribute("total", Extensions.toFormattedCurrency(value));
        model.addAttribute("freeShipping", Extensions.toFormattedCurrency(shoppingValueForFreeShipping));

        return "bag";
    }

    @GetMapping("/removeFromCart/{bid}")
    public String removeFromBag(@PathVariable @NotNull Long bid) {
        var user = getAuthenticatedUser();

        if(user == null) return login;

        var cartItem = _shoppingCarts.findById(bid);

        if(cartItem.isEmpty() || !cartItem.get().getUserId().equals(user.getId())) return notFound;

        _shoppingCarts.delete(cartItem.get());

        return redirect("bag?removed");
    }
}
