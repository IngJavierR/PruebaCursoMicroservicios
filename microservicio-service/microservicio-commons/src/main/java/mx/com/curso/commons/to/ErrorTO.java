package mx.com.curso.commons.to;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class ErrorTO implements Serializable {

    private String errorCode;
    private String errorMessage;
    private String userError;
    private String info;
}