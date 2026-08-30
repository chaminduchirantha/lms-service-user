package lk.ijse.gdse.userservice.Service.impl;

import lk.ijse.gdse.userservice.Entity.Role;
import lk.ijse.gdse.userservice.Entity.User;
import lk.ijse.gdse.userservice.Service.UserService;
import lk.ijse.gdse.userservice.dto.UserDto;
import lk.ijse.gdse.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email is already registered: " + userDTO.getEmail());
        }

        User user = new User();
        String uName = userDTO.getUsername() != null ? userDTO.getUsername() : userDTO.getName();
        user.setUserName(uName);
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : Role.USER);

        User savedUser = userRepository.save(user);
        UserDto result = modelMapper.map(savedUser, UserDto.class);
        result.setUsername(savedUser.getUserName());
        result.setName(savedUser.getUserName());
        result.setPassword(null);
        return result;
    }

    @Override
    public UserDto loginUser(UserDto loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password!"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password!");
        }

        UserDto responseDto = modelMapper.map(user, UserDto.class);
        responseDto.setUsername(user.getUserName());
        responseDto.setName(user.getUserName());
        responseDto.setPassword(null);
        return responseDto;
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        UserDto dto = modelMapper.map(user, UserDto.class);
        dto.setUsername(user.getUserName());
        dto.setName(user.getUserName());
        dto.setPassword(null);
        return dto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserDto dto = modelMapper.map(user, UserDto.class);
            dto.setUsername(user.getUserName());
            dto.setName(user.getUserName());
            dto.setPassword(null);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(String id, UserDto userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (userDTO.getUsername() != null || userDTO.getName() != null) {
            String uName = userDTO.getUsername() != null ? userDTO.getUsername() : userDTO.getName();
            user.setUserName(uName);
        }
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null) user.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(user);
        UserDto dto = modelMapper.map(updatedUser, UserDto.class);
        dto.setUsername(updatedUser.getUserName());
        dto.setName(updatedUser.getUserName());
        dto.setPassword(null);
        return dto;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
