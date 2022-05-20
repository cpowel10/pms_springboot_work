package com.revature.pms.dao;

import com.revature.pms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product,Integer> {
    //declare the methods
    //select * from product where productName=?
    public List<Product> findByProductName(String productName);

    //select * from product where price=?
    public List<Product> findByPrice(int price);

    //select * from product where qoh=?
    public List<Product> findByQoh(int qoh);

    public List<Product> findByPriceBetween(int minimumPrice, int maximumPrice);

    //select * from product where qoh < ?
    public List<Product> findByQohLessThan(int maxQoh);

    //select * from product where qoh > ?
    public List<Product> findByQohGreaterThan(int minQoh);

    //select * from product where productName = ?
    //select from model class, not from table
    @Query("select p from Product p where productName = ?1")
    public List<Product> getProductByName(String productName);

    //Lakme
    //100
    @Query("SELECT p FROM Product p WHERE p.productName = ?1 AND p.price = ?2")
    public List<Product> findAll(String productName, int price);

}
