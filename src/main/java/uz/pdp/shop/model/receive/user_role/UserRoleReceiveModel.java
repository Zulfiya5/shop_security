package uz.pdp.shop.model.receive.user_role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleReceiveModel {
    private String name;
    private int permissionId;

}
