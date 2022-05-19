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

import java.util.ArrayList;

@RestController
@RequestMapping("product")
public class ProductController {
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

    //Method will save a product to DB
    @PostMapping //localhost:8084/product    ---HTTP method POST
    public ResponseEntity<String> saveProduct(@RequestBody Product product){
        ResponseEntity responseEntity=null;
        if(productService.isProductExists(product.getProductId())){
            responseEntity = new ResponseEntity<String>("Cannot save because product with product id: "+product.getProductId()+" already exists", HttpStatus.CONFLICT); //409
        }
        else{
            result = productService.addProduct(product);
            if(result){
                responseEntity = new ResponseEntity<String>("Successfully saved your product: "+product, HttpStatus.OK); //200
            }
            else{
                responseEntity = new ResponseEntity<String>("Cannot save either price or qoh is negative", HttpStatus.NOT_ACCEPTABLE); //406
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
    public String allProducts(){
        return "All Products";
    }

    @GetMapping("{pId}") //localhost:8084/product/89
    public Product getProduct(@PathVariable("pId")int productId){
        System.out.println("Fetching details about: "+productId);
        Product product = new Product(productId,"Lakme",88,99);
        return product;
    }

    @GetMapping("/searchProductByName/{pName}") //localhost:8084/product/searchProductByName/Lakme
    public Product getProductByName(@PathVariable("pName")String productName){
        System.out.println("Fetching details about: "+productName);
        Product product = new Product(-1,productName,88,99);
        return product;
    }

    @GetMapping("/filterProductByPrice/{priceMin}/{priceMax}") //localhost:8084/product/filterProductByPrice/100/500
    public String filterProductByPrice(@PathVariable("priceMin")int min, @PathVariable("priceMax")int max){
        //System.out.println("Here is the result for product in price range of "+min+" and "+max);
        Product product = new Product(1,"Laptop",88,200);
        Product product2 = new Product(2,"Computer",88,300);
        Product product3 = new Product(3,"Bottle",88,555);
        Product product4 = new Product(4,"Plush",88,123);
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        for (int i = 0 ; i < products.size(); i++){
            if(products.get(i).getPrice() > min && products.get(i).getPrice() < max){
                break;
            }
            else
                products.remove(products.get(i));
        }

        return "Here is the result for product in price range of "+min+" and "+max+" \n"+products.toString();
    }

    @GetMapping("/outOfStockProductDetails/{qoh}") //localhost:8084/product/outOfStockProductDetails/50
    public String outOfStockProductDetails(@PathVariable("qoh")int qoh){
        return "Order with qoh less than "+qoh+" should be reordered immediately";
    }

    @DeleteMapping("{pId}") //localhost:8084/product/89
    public String deleteProduct(@PathVariable("pId")int productId){
        return "Deleting details by product ID: "+productId;
    }

    //This method will update a product in DB
    @PutMapping //localhost:8084/product    ---HTTP method POST
    public String updateProduct(@RequestBody Product product){
        System.out.println("Updating details of: "+product);
        //call the methods to update product
        //return "Cannot update your product because either price or qoh is negative";

        return "Successfully updated product: "+product;
    }
}
