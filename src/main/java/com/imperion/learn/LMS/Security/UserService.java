package com.imperion.learn.LMS.Security;

import com.imperion.learn.LMS.CustomException.ResourceNotFoundException;
import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.PayLoad.SignUpDto;
import com.imperion.learn.LMS.PayLoad.UserDto;
import com.imperion.learn.LMS.Repositories.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService implements UserDetailsService {
    //dependency inject User Repository and ModelMapper and (AuthenticaionManager(for login) in AuthLogin service)
    //create a bean of AuthenticationManager inn webSecurityConfig
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("User with email "+ username +" not found"));
    }




    //we need user by id for authentication used in jwtfilters
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id "+userId+" Not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
      Optional<User> emailprsent= userRepository.findByEmail(signUpDto.getEmail());
      if(emailprsent.isPresent()){
          throw new ResourceNotFoundException("User Existes,You can Login directly");
      }
      //make sure you encode password
      User createdNewUserSignUp=modelMapper.map(signUpDto,User.class);
      createdNewUserSignUp.setPassword(passwordEncoder.encode(createdNewUserSignUp.getPassword()));
      User savedUser=userRepository.save(createdNewUserSignUp);
      return modelMapper.map(savedUser, UserDto.class);
    }



    //-------------------------------------Oauth-----------------------------------
    //these useremail and save if or Oauth2handlers and it comes from handler
    public User getUsrByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public List<UserDto> getAllUsers() {
        List<User>allusers=userRepository.findAll();
        return  allusers
                .stream()
                .map(entity->modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }


}
