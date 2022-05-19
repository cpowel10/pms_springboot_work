package com.revature.pms.utilities;

import org.springframework.stereotype.Component;

@Component
public class GenerateRandomBigNumber {
    public double getRandomBiggerNumber(){
        return Math.random()*900;
    }
}
