package pl.pwr.news.webapp.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jakub on 3/4/16.
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO<T> {

    public final static String RESULT_OK = "000";
    public final static String MESSAGE_OK = "OK";

    String result;
    String message;
    T value;

    public ResponseDTO(T value) {
        result = RESULT_OK;
        message = MESSAGE_OK;
        this.value = value;
    }
}
