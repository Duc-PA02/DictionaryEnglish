package com.example.appdictionaryghtk.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class DefaultResponse<D> {

    private Boolean success;
    private String message;
    private D data;

    public static <D> DefaultResponse<D> success(String message,D data) {
        DefaultResponse<D> response = new DefaultResponse<>();
        response.success = Boolean.TRUE;
        response.message = message;
        response.data = data;
        return response;
    }

    public static <D> DefaultResponse<D> error(String message, D data) {
        DefaultResponse<D> response = new DefaultResponse<>();
        response.success = Boolean.FALSE;
        response.message = message;
        response.data = data;
        return response;
    }

    public static DefaultResponse<Object> error(String message) {
        return error(message, null);
    }

}
