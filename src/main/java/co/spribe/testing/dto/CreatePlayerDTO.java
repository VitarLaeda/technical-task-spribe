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
public class CreatePlayerDTO {

    @JsonProperty
    int age;
    @JsonProperty
    String editor;
    @JsonProperty
    String gender;
    @JsonProperty
    Integer id;
    @JsonProperty
    String login;
    @JsonProperty
    String password;
    @JsonProperty
    String role;
    @JsonProperty
    String screenName;

    public CreatePlayerDTO(String editor, int age, String gender,String login, String password, String role, String screenName) {
        this.editor = editor;
        this.age = age;
        this.gender = gender;
        this.login = login;
        this.password = password;
        this.role = role;
        this.screenName = screenName;
    }
}
