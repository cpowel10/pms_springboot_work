package com.revature.pms.utilities;

import org.springframework.stereotype.Component;

@Component
public class NegativeValue {

    public boolean checkNegativeValue(int num){
        return num<0;
    }
}
