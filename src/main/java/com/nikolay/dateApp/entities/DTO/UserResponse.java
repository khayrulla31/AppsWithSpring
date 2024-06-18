package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse implements Serializable {
    private String userName;
    private List<RoleResponse> roles;

}
