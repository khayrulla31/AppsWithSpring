package com.nikolay.dateApp.entities.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
@Data
public class ChangePasswordForm implements Serializable {
        @NotBlank(message = "enter your current password")
        private String oldPassword;
        @NotBlank(message = "enter new password")
        private String newPassword;
        @NotBlank(message = "confirm new password")
        @Transient
        private String confirmNewPassword;


}
