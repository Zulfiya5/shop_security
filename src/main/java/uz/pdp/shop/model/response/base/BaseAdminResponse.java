package uz.pdp.shop.model.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class BaseAdminResponse extends BaseResponse{
    public BaseAdminResponse(String message, int status, boolean success, Object data) {
        super(message, status, success, data);
    }
}
