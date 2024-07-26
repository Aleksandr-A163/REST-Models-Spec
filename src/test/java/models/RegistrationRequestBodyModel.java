package models;

import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class RegistrationRequestBodyModel {

    private String userName;
    private String password;

    public RegistrationRequestBodyModel() {
        Faker faker = new Faker();
        this.userName = faker.name().username();
        this.password = "11Test55!";
    }

}