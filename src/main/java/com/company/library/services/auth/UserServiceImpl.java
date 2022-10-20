package com.company.library.services.auth;

import com.company.library.configs.ApplicationContextHolder;
import com.company.library.dao.AbstractDAO;
import com.company.library.dao.UserDAO;
import com.company.library.domains.User;
import com.company.library.dto.auth.UserCreateDTO;
import com.company.library.dto.auth.UserDTO;
import com.company.library.dto.auth.UserLoginDTO;
import com.company.library.dto.auth.UserUpdateDTO;
import com.company.library.exceptions.AuthenticationException;
import com.company.library.exceptions.InvalidInputException;
import com.company.library.exceptions.NotFoundException;
import com.company.library.utils.BaseUtils;

import java.util.Optional;


/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 8:33 PM (Saturday)
 * lib16/IntelliJ IDEA
 */

public class UserServiceImpl extends AbstractDAO<UserDAO> implements UserService {

    private static UserServiceImpl userService;
    private final BaseUtils baseUtils = ApplicationContextHolder.getBean(BaseUtils.class);


    private UserServiceImpl() {
        super(ApplicationContextHolder.getBean(UserDAO.class));
    }

    @Override
    public UserDTO create(UserCreateDTO userCreateDTO) throws InvalidInputException {
        Optional<User> byEmail = dao.findByEmail(userCreateDTO.getEmail());
        if (byEmail.isPresent()) {
            throw new InvalidInputException("User by email %s already exists".formatted(byEmail.get()));
        }
        User user = User.builder()
                .email(userCreateDTO.getEmail())
                .name(userCreateDTO.getName())
                .password(baseUtils.encode(userCreateDTO.getPassword()))
                .surname(userCreateDTO.getSurname())
                .build();
        User savedUser = dao.save(user);
        return UserDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .surname(savedUser.getSurname())
                .name(savedUser.getName())
                .build();
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public void delete(long l) {

    }

    @Override
    public void update(UserUpdateDTO dto) {

    }

    @Override
    public UserDTO getByEmail(String emailAddress) throws NotFoundException {
        Optional<User> byEmail = dao.findByEmail(emailAddress);
        User user = byEmail.orElseThrow(() -> new NotFoundException("user not found by email"));
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

    @Override
    public UserDTO login(UserLoginDTO userLoginDTO) throws InvalidInputException, AuthenticationException {
        String email = userLoginDTO.getEmail();
        Optional<User> byEmail = dao.findByEmail(email);
        User user = byEmail.orElseThrow(() -> new InvalidInputException("user not found by email %s".formatted(email)));
        if (!baseUtils.matchPassword(userLoginDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Bad credentials");
        }
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
