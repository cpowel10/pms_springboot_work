package com.revature.pms.services;

import com.revature.pms.dao.ProductDAO;
import com.revature.pms.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDAO productDAO;

    @Override
    public boolean addProduct(Product product) {
        System.out.println("Adding product in service");
        productDAO.save(product);
        return true;
    }

    @Override
    public boolean deleteProduct(int productId) {
        System.out.println("Deleting product in service");

        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public Product getProduct(int productId) {
        return null;
    }

    @Override
    public boolean isProductExists(int productId) {
        return false;
    }

    @Override
    public List<Product> getProduct(String productName) {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public List<Product> filterProductByPrice(int minimumPrice, int maximumPrice) {
        return null;
    }

    @Override
    public List<Product> getProductByPrice(int price) {
        return null;
    }

    @Override
    public List<Product> getProductByQOH(int qoh) {
        return null;
    }

    @Override
    public List<Product> getProductByGreaterQOH(int greaterQoh) {
        return null;
    }
}
