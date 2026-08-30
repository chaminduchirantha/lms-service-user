package lk.ijse.gdse.userservice.Service;

import lk.ijse.gdse.userservice.dto.UserDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDTO);

    UserDto loginUser(UserDto loginRequestDTO);

    UserDto getUserById(String id);

    List<UserDto> getAllUsers();

    UserDto updateUser(String id, UserDto userDTO);

    void deleteUser(String id);
}
