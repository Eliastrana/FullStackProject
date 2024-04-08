package edu.ntnu.idatt2105.SpringbootBackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for updating a user's password. It contains the old password for verification
 * and the new password to be set.
 * 
 * @author Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDTO {

    @Schema(description = "The current password of the user.", example = "currentPassword123", required = true)
    private String oldPassword;

    @Schema(description = "The new password that the user wants to set.", example = "newSecurePassword456", required = true)
    private String newPassword;
}
