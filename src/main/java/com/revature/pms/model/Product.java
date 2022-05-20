package com.revature.pms.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Component
@Entity
@Table(name="product200",schema="public")
public class Product {
    @Id
    private int productId;
    private String productName;
    private int qoh;
    private int price;

    public Product(){
        System.out.println("Product default constructor called");
    }

    public String displayMessage(){
        return "This message is coming from the Product class";
    }
}
