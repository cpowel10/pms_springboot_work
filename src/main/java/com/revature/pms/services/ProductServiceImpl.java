package com.revature.pms.services;

import com.revature.pms.dao.ProductDAO;
import com.revature.pms.model.Product;
import com.revature.pms.utilities.NegativeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDAO productDAO;

    @Autowired
    NegativeValue negativeValue;

    @Override
    public boolean addProduct(Product product) {
        if(negativeValue.checkNegativeValue(product.getQoh()) || negativeValue.checkNegativeValue(product.getPrice())){
            return false;
        }
        productDAO.save(product);
        return true;
    }

    @Override
    public boolean deleteProduct(int productId) {
        productDAO.deleteById(productId);
        return true;
    }

    @Override
    public boolean updateProduct(Product product) {
        if(negativeValue.checkNegativeValue(product.getQoh()) || negativeValue.checkNegativeValue(product.getPrice())){
            return false;
        }
        productDAO.save(product);
        return true;
    }

    @Override
    public Product getProduct(int productId) {
        Product pr = productDAO.getById(productId);
        return pr;
    }

    @Override
    public boolean isProductExists(int productId) {
        return productDAO.existsById(productId);
    }

    @Override
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    //by default these are not exposed
    @Override
    public List<Product> getProduct(String productName) {
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
