package pl.pwr.news.webapp.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jakub on 3/4/16.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    public final static String RESULT_OK = "000";
    public final static String MESSAGE_OK = "OK";

    String result;
    String message;
    T value;

    public Response(String result, String message) {
        this.result = result;
        this.message = message;
        value = null;
    }

    public Response(T value) {
        result = RESULT_OK;
        message = MESSAGE_OK;
        this.value = value;
    }
}
