package oris.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String hashPassword;
}