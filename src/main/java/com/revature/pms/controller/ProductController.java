package com.revature.pms.controller;

import com.revature.pms.dao.ProductDAO;
import com.revature.pms.model.Product;
import com.revature.pms.services.ProductService;
import com.revature.pms.utilities.GenerateRandomBigNumber;
import com.revature.pms.utilities.GenerateRandomNumber;
import com.revature.pms.utilities.NegativeValue;
import com.revature.pms.utilities.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController implements Serializable {
    @Autowired()
    ProductDAO productDAO;

    @Autowired()
    Product product ;

    @Autowired(required = false) //If bean is available, then inject it ---if bean is unavailable, then don't give an error
    PasswordHashing passwordHashing;

    @Autowired
    GenerateRandomNumber randomNumber = new GenerateRandomNumber();

    @Autowired
    ProductService productService;

    boolean result;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    public ProductController(){
        System.out.println("Product controller called");
    }

    @PostConstruct   //lifecycle method
    public void callMeFirst(){
        //for initilize
        result=true;
        System.out.println("Call me First called");
    }

    @PreDestroy
    public void callMeLast(){
        
    }

    //Method will save a product to DB
    @PostMapping //localhost:8084/product    ---HTTP method POST
    public ResponseEntity<String> saveProduct(@RequestBody Product product){
        ResponseEntity responseEntity=null;
        LOGGER.info("INFO - Save product started the execution");
        if(productService.isProductExists(product.getProductId())){
            LOGGER.warn("Product with product id: "+product.getProductId()+" already exists");
            responseEntity = new ResponseEntity<String>("Cannot save because product with product id: "+product.getProductId()+" already exists", HttpStatus.CONFLICT); //409
        }
        else{
            result = productService.addProduct(product);
            if(result){
                responseEntity = new ResponseEntity<String>("Successfully saved your product: "+product, HttpStatus.OK); //200
                LOGGER.info("Product with product id: "+product.getProductId()+" saved successfully");
            }
            else{
                responseEntity = new ResponseEntity<String>("Cannot save either price or qoh is negative", HttpStatus.NOT_ACCEPTABLE); //406
                LOGGER.error("Product with product id: "+product.getProductId()+" cannot be saved because of negative qoh or price");
            }
        }
        return responseEntity;
    }

    @GetMapping("/home") //localhost:8084/product/home
    public String home(){
        double result = randomNumber.getRandomNumber();

        return "Welcome To Product App - "
                +product.displayMessage()+"---"
                +passwordHashing.getHashedPassword()
                +" Random number for this request is: "+result;
    }

    @GetMapping //localhost:8084/product
    public ResponseEntity<List<Product>> getProducts(){
        ResponseEntity responseEntity = null;
        LOGGER.info("Get products started the execution");
        List<Product> products = new ArrayList<Product>();
        products = productService.getProducts();
        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
    }

    @GetMapping("{pId}") //localhost:8084/product/89
    public ResponseEntity<Product> getProduct(@PathVariable("pId")int productId){
        ResponseEntity responseEntity = null;
        LOGGER.info("Get product by product ID started the execution");
        Product product1 = new Product();
        if(productService.isProductExists(productId)){
            product1 = productService.getProduct(productId);
            LOGGER.info("Product with product id: "+productId+" successfully found");
            responseEntity = new ResponseEntity<Product>(product1,HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<Product>(product1,HttpStatus.NO_CONTENT);
            LOGGER.error("Product with product id: "+product.getProductId()+" does not exist");
        }
        return responseEntity;
    }

    @GetMapping("/searchProductByName/{pName}") //localhost:8084/product/searchProductByName/Lakme
    public List<Product> getProductByName(@PathVariable("pName")String productName){
        return productService.getProduct(productName);
    }

    @GetMapping("/filterProductByPrice/{priceMin}/{priceMax}") //localhost:8084/product/filterProductByPrice/100/500
    public List<Product> filterProductByPrice(@PathVariable("priceMin")int min, @PathVariable("priceMax")int max){
        return productService.filterProductByPrice(min,max);
    }

    @GetMapping("/qohLessThan/{qoh}") //localhost:8084/product/qohLessThan/50
    public List<Product> qohLessThanProductDetails(@PathVariable("qoh")int qoh){
        return productService.getProductByLessQOH(qoh);
    }

    @GetMapping("/qohGreaterThan/{qoh}") //localhost:8084/product/qohGreaterThan/50
    public List<Product> qohGreaterThanProductDetails(@PathVariable("qoh")int qoh){
        return productService.getProductByGreaterQOH(qoh);
    }

    @DeleteMapping("{pId}") //localhost:8084/product/89
    public ResponseEntity<String> deleteProduct(@PathVariable("pId")int productId){
        ResponseEntity responseEntity = null;
        if(productService.isProductExists(productId)){
            result = productService.deleteProduct(productId);
            responseEntity = new ResponseEntity<String>("Successfully deleted product with ID: "+productId,HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>("Unable to delete product because there " +
                    "is no product with productId: "+productId,HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    //This method will update a product in DB
    @PutMapping //localhost:8084/product    ---HTTP method POST
    public ResponseEntity<String> updateProduct(@RequestBody Product product){
        ResponseEntity responseEntity=null;
        if(!productService.isProductExists(product.getProductId())){
            responseEntity = new ResponseEntity<String>("Cannot save because product with product id: "+product.getProductId()+" does not exist exists", HttpStatus.NO_CONTENT); //409
        }
        else{
            result = productService.updateProduct(product);
            if(result){
                responseEntity = new ResponseEntity<String>("Successfully updated your product: "+product, HttpStatus.OK); //200
            }
            else{
                responseEntity = new ResponseEntity<String>("Cannot save either price or qoh is negative", HttpStatus.NOT_ACCEPTABLE); //200
            }
        }
        return responseEntity;
    }
}
