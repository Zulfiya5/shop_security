package uz.pdp.shop.entity.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {
    WRITE,
    DELETE,
    EDIT,
    READ,
    ALL
}
