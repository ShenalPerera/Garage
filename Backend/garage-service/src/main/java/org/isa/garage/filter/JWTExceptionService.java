package org.isa.garage.filter;

import org.isa.garage.exception.GarageJWTException;
import org.springframework.stereotype.Service;

@Service
public class JWTExceptionService {

    public void throwException(String message){
        throw new GarageJWTException(message);
    }
}
