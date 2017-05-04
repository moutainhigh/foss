package com.deppon.foss.module.pickup.pricing.api.shared.dto;

/**
 * Created by 343617 on 2016/10/28.
 * 和CUBC交互后封装数据的类
 */
public class ResultCUBCDto {
    private int statusCode;
    private String jsonResponse;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }
}
