package lk.ijse.gdse.userservice.dto;


import lk.ijse.gdse.userservice.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String name;
    private String email;
    private String password;
    private Role role;
}
