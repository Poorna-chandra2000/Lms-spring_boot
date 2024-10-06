package com.imperion.learn.LMS.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginResponseDto {

  private Long id;
  private String accessToken;
  private String refreshToken;
}
