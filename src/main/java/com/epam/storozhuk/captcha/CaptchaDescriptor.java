package com.epam.storozhuk.captcha;

public class CaptchaDescriptor {
    private long creationTime;
    private String codeValue;

    public CaptchaDescriptor() {
    }

    public CaptchaDescriptor(long creationTime, String codeValue) {
        this.creationTime = creationTime;
        this.codeValue = codeValue;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
}
