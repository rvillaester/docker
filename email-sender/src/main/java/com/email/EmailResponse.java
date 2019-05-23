package com.email;

public class EmailResponse {

    private int status;
    private String message;

    public EmailResponse(){}

    public EmailResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public static EmailResponse failedResponse(){
        return new EmailResponse(-1, "Fail");
    }

    public String getMessage() {
        return message;
    }

    public boolean isSucceed(){
        return status == 0;
    }
}
