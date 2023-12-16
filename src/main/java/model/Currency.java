package model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonPropertyOrder({"id", "code", "fullName", "sign"})
public class Currency {
    private Long id;
    @NonNull
    private String code;
    @NonNull
    private String fullname;
    @NonNull
    private String sign;

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", fullname='" + fullname + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
