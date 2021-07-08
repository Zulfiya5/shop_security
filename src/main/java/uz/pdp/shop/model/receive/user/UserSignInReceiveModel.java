package uz.pdp.shop.model.receive.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignInReceiveModel {

    private String username;
    private String password;
}
