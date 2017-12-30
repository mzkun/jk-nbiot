package com.goldcard.nbiot.web.home.common;

public class ReturnBody {
    
    public ReturnBody(){

    }
    
    public ReturnBody(boolean result, String message){
        this.result = result;
        this.message = message;
    }
   
    /**
     * 结果
     * */
    private boolean result;
    
    /**
     * 错误信息
     * */
    private String message;

    
    public boolean getResult() {
        return result;
    }

    
    public void setResult(boolean result) {
        this.result = result;
    }

    
    public String getMessage() {
        return message;
    }

    
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
