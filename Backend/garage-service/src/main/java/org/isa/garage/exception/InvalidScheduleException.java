package org.isa.garage.exception;

public class InvalidScheduleException extends RuntimeException{

    public InvalidScheduleException(){
        super("The status of the schedule is invalid. Please check.");
    }
    public InvalidScheduleException(String message){
        super(message);
    }
}
