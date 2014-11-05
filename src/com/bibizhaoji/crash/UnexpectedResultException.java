package com.bibizhaoji.crash;

public class UnexpectedResultException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    private int code;
    
    
    public UnexpectedResultException(String message)
    {
        super(message);
    }
    
    public int getCode()
    {
        return code;
    }
    
    public void setCode(int code)
    {
        this.code = code;
    }
}
