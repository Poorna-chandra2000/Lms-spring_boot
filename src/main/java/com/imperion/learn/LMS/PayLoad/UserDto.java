package com.imperion.learn.LMS.PayLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.Entities.Enrolled;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
}
