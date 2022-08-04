package models.lombok;

import lombok.Data;

@Data
public class ResponseLombokModel {

    private String email;
    private String password;
    private String token;
    private String error;
}
