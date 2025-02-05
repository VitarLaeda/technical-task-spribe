package co.spribe.testing.dto;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonInclude;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeletePlayerRequestDTO {

    @JsonProperty
    Integer playerId;
}
