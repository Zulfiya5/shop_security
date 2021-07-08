package uz.pdp.shop.model.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class BaseRestResponse extends BaseResponse{
    public BaseRestResponse(String message, int status, boolean success, Object data) {
        super(message, status, success, data);
    }
}
