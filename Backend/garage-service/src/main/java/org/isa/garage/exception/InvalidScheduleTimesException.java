package org.isa.garage.exception;

public class InvalidScheduleTimesException extends RuntimeException{
    public InvalidScheduleTimesException(){
        super("Invalid Schedule Start time or End time");
    }

    public InvalidScheduleTimesException(String message){
        super(message);
    }
}
