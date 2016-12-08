package com.ddf.service;

import com.ddf.SenacAcEcommerceApplication;
import com.ddf.domain.Order;
import com.ddf.domain.OrderStatus;
import com.ddf.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    public OrderService(OrderRepository orderRepository, JavaMailSender mailSender) {
        this.orderRepository = orderRepository;
        this.mailSender = mailSender;
    }

    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    public List<Order> findAllOrderById() {
        return this.orderRepository.findAllByOrderByIdDesc();
    }

    public Order get(Long id) {
        return this.orderRepository.findOne(id);
    }


    public Order save(Order order) {

        Order orderBefore = this.get(order.getId());

        if (!Objects.equals(orderBefore.getStatus().getId(), OrderStatus.PAYMENT_CONFIRMED)
            && Objects.equals(order.getStatus().getId(), OrderStatus.PAYMENT_CONFIRMED)
        ) {
            String text = "Olá! O pagamento da sua compra #" + order.getId() + " foi aprovado. Você já pode fazer o download do material";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(this.emailFrom);
            message.setTo(order.getCustomer().getUser().getEmail());
            message.setSubject("AC E-Commerce: #" + order.getId() + " - Pagamento aprovado");
            message.setText(text);
            mailSender.send(message);
        }

        return this.orderRepository.save(order);
    }
}
