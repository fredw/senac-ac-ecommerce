package com.ddf.controller.panel;

import com.ddf.domain.Order;
import com.ddf.service.OrderItemService;
import com.ddf.service.OrderService;
import com.ddf.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/painel/pedidos")
public class PanelOrderController {

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final OrderStatusService orderStatusService;

    @Autowired
    public PanelOrderController(
        OrderService orderService,
        OrderItemService orderItemService,
        OrderStatusService orderStatusService
    ) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.orderStatusService = orderStatusService;
    }

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("orders", this.orderService.findAllOrderById());
        return "panel/order/index";
    }

    @RequestMapping("/{id}")
    public String form(@PathVariable Long id, Model model) {
        Order order = this.orderService.get(id);
        model.addAttribute("statuses", this.orderStatusService.findAll());
        model.addAttribute("order", order);
        model.addAttribute("orderItens", this.orderItemService.findByOrder(order));
        return "panel/order/form";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String formSave(
        @Valid Order order,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("order", order);
            return "panel/order/form";
        }

        this.orderService.save(order);
        return "redirect:/painel/pedidos";
    }
}
