package uz.pdp.shop.entity.order_product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderProductState {

    CREATED(1,"buyurtma kelib tushdi"),
    ACCEPTED(6,"buyurtma user tomonidan tasdiqlandi"),
    CHECK(2,"buyurtma ko'rib chiqilmoqda"),
    IN_PROCESSING(3,"buyurtma yetkazib berish jarayonida"),
    SUCCESS(4,"buyurtma muvafaqqiyatli yetkazib berildi"),
    CANCEL(5,"buyurtma bekor qilindi");

    private int stateId;
    private String message;


}
