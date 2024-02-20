package com.manrel.manrelmonitoringmono.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

//IMPORTANT!!! DO NOT CHANGE THIS ORDER
@Getter
@Setter
@JsonPropertyOrder({"success", "data", "error"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private boolean success = true;
    private T data;
    private ResponseError error;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public void setError(ResponseError error) {
        success = false;
        this.error = error;
    }
}
