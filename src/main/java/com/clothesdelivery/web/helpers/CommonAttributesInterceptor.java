package com.clothesdelivery.web.helpers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CommonAttributesInterceptor implements HandlerInterceptor {
    @Value("${clothes.whatsapp.number}")
    private String shoppingWhatsappNumber;

    @Value("${clothes.email.address}")
    private String shoppingEmailAddress;

    @Autowired
    public CommonAttributesInterceptor(HttpServletRequest httpRequest) {
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler,
                           ModelAndView modelAndView) {
        if (modelAndView != null) {
            modelAndView.addObject("hostUrl", HttpRequests.getHostUrl(request));
            modelAndView.addObject("hostPath", HttpRequests.getRequestUrlWithQuery(request));
            modelAndView.addObject("email", shoppingEmailAddress);
            modelAndView.addObject("phone", shoppingWhatsappNumber);
            modelAndView.addObject("request", request);

            /*modelAndView.addObject("isAuthenticated", _authManager.isAuthenticated());

            if(_authManager.isAuthenticated()) {
                modelAndView.addObject("authenticatedUser", _authManager.getAuthenticatedUser());
            }*/
        }
    }
}
