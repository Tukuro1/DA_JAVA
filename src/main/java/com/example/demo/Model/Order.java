package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String paymentMethod; // Thêm trường này để chọn phương thức thanh toán

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
