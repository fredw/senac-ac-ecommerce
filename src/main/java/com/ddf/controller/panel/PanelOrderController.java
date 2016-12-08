package com.ddf.controller.panel;

import com.ddf.domain.Order;
import com.ddf.service.OrderItemService;
import com.ddf.service.OrderService;
import com.ddf.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("panel/order/index");
        mv.addObject("orders", this.orderService.findAllOrderById());
        return mv;
    }

    @RequestMapping("/{id}")
    public ModelAndView form(@PathVariable Long id) {
        Order order = this.orderService.get(id);
        ModelAndView mv = new ModelAndView("panel/order/form");
        mv.addObject("statuses", this.orderStatusService.findAll());
        mv.addObject("order", order);
        mv.addObject("orderItens", this.orderItemService.findByOrder(order));
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ModelAndView formSave(
        @Valid Order order,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("panel/order/form");
            mv.addObject("order", order);
            return mv;
        }

        this.orderService.save(order);
        return new ModelAndView("redirect:/painel/pedidos");
    }
}
