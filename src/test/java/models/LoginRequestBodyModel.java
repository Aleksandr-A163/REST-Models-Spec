package models;

import lombok.Data;

@Data
public class LoginRequestBodyModel {

    private String userName;
    private String password;

}