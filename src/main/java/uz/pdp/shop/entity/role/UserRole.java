package uz.pdp.shop.entity.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static uz.pdp.shop.entity.role.UserPermission.*;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN(Arrays.asList(DELETE,EDIT,READ,WRITE)),
    USER(Collections.singletonList(READ)),
    SUPER_ADMIN(Collections.singletonList(ALL));

    private List<UserPermission> permissions;
}
