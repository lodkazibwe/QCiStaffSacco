package com.qualitychemicals.qciss.profile.DTO;

import com.qualitychemicals.qciss.profile.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    @NotNull
    @Size(min=3, message = "user name at least three Characters")
    private String userName;
    @NotNull
    @Size(min=6, message = "passKey at least six Characters")
    private String password;
    private String status;
    private List<Role> role;
}
