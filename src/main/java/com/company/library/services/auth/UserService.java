package com.company.library.services.auth;

import com.company.library.domains.User;
import com.company.library.dto.auth.UserCreateDTO;
import com.company.library.dto.auth.UserDTO;
import com.company.library.dto.auth.UserLoginDTO;
import com.company.library.dto.auth.UserUpdateDTO;
import com.company.library.exceptions.AuthenticationException;
import com.company.library.exceptions.InvalidInputException;
import com.company.library.exceptions.NotFoundException;


/**
 * @author "Otajonov Dilshodbek
 * @since 7/9/22 8:32 PM (Saturday)
 * lib16/IntelliJ IDEA
 */
public interface UserService {

    UserDTO create(UserCreateDTO userCreateDTO) throws InvalidInputException;

    User get(long id);

    void delete(long l);

    void update(UserUpdateDTO dto);

    UserDTO login(UserLoginDTO userLoginDTO) throws InvalidInputException, AuthenticationException;

    UserDTO getByEmail(String emailAddress) throws NotFoundException;
}
