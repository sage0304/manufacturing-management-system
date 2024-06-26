package com.manufacturing.manufacturingmanagementsystem.dtos;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {

    private String orderId;

    private Long productId;

    private Integer quantity;

    private Float totalPrice;
}