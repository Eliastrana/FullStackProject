package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDTO {
  private String oldPassword;
  private String newPassword;
}
