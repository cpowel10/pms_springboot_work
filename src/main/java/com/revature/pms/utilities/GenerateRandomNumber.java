package com.revature.pms.utilities;

import org.springframework.stereotype.Component;

@Component
public class GenerateRandomNumber {

    public double getRandomNumber(){
        return Math.random();
    }
}
