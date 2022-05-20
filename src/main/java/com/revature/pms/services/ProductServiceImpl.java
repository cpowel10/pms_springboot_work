package com.revature.pms.services;

import com.revature.pms.dao.ProductDAO;
import com.revature.pms.model.Product;
import com.revature.pms.utilities.NegativeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDAO productDAO;

    @Autowired
    NegativeValue negativeValue;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public boolean addProduct(Product product) {
        LOGGER.info("Adding product to table");
        if(negativeValue.checkNegativeValue(product.getQoh()) || negativeValue.checkNegativeValue(product.getPrice())){
            LOGGER.error("Cannot add product with productID: "+product.getProductId()+" because either qoh or price is negative");
            return false;
        }
        productDAO.save(product);
        LOGGER.info("Successfully added product with productID: "+product.getProductId()+" to table");
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
        return productDAO.findByProductName(productName);
    }

    @Override
    public List<Product> filterProductByPrice(int minimumPrice, int maximumPrice) {
        return productDAO.findByPriceBetween(minimumPrice,maximumPrice);
    }

    @Override
    public List<Product> getProductByPrice(int price) {
        return productDAO.findByPrice(price);
    }

    @Override
    public List<Product> getProductByQOH(int qoh) {
        return productDAO.findByQoh(qoh);
    }

    @Override
    public List<Product> getProductByGreaterQOH(int greaterQoh) {
        return productDAO.findByQohGreaterThan(greaterQoh);
    }

    @Override
    public List<Product> getProductByLessQOH(int lessQoh) {
        return productDAO.findByQohLessThan(lessQoh);
    }
}
