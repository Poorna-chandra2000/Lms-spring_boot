package com.imperion.learn.LMS.PayLoad;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {
    private String email;
    private String password;
    //handle using Auth controller
    //i.e again post request
}
