package com.manufacturing.manufacturingmanagementsystem.controllers;

import com.manufacturing.manufacturingmanagementsystem.dtos.UsersDTO;
import com.manufacturing.manufacturingmanagementsystem.dtos.responses.ApiResponse;
import com.manufacturing.manufacturingmanagementsystem.dtos.responses.ResponseObject;
import com.manufacturing.manufacturingmanagementsystem.dtos.responses.Role.RoleResponse;
import com.manufacturing.manufacturingmanagementsystem.dtos.responses.UserResponse;
import com.manufacturing.manufacturingmanagementsystem.mapper.RoleMapper;
import com.manufacturing.manufacturingmanagementsystem.models.UsersEntity;
import com.manufacturing.manufacturingmanagementsystem.service.Users.UsersServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllers {
    private final UsersServices userService;

    RoleMapper roleMapper;

    @PostMapping("/create")
    public ResponseEntity<?> insertUser(@Valid @RequestBody UsersDTO userDto) {
        try {
            Map<String, Object> newUser = userService.insertUser(userDto);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(newUser)
                    .message("Add user successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllRoles")
//    @PreAuthorize("hasAnyAuthority('SCOPE_CHAIRMAN', 'SCOPE_ACCOUNTANT', 'SCOPE_PRODUCT_MANAGER')")
    @PreAuthorize("hasAnyAuthority('SCOPE_CHAIRMAN')")
    public List<UsersEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserInformationById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UsersEntity user = userService.getUserById(id);
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .message("Get user successfully")
                    .result(UserResponse.fromUser(user))
                    .build());
        } catch (Exception e) {
            // Xử lý lỗi và trả về phản hồi lỗi (status code 500)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getUserInformationByEmail")
    public UsersEntity getUserInformationByEmail(String email) {
        return userService.findUserbyEmail(email);
    }

    @GetMapping("/getUserInformationByRole")
    public UsersEntity getUserInformationByRole(String roleName) {
        return userService.findUserbyRole(roleName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UsersDTO userDto) {
        try {
            Map<String, Object> user = userService.updateUser(id,userDto);

            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(user)
                            .message("Update user successfully")
                            .status(HttpStatus.OK)
                            .build());
        }catch (Exception e) {
            // Xử lý lỗi và trả về phản hồi lỗi (status code 500)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reset/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @Valid @RequestBody UsersDTO userDto) {
        try {
            UsersEntity user = userService.resetPassword(id, userDto);

            return ResponseEntity.ok(ResponseObject.builder()
                                .data(user)
                                .message("Update password successfully")
                                .status(HttpStatus.OK)
                                .build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/getMyInfo")
    public ResponseEntity<ApiResponse> getMyInfo() {
        UserResponse userInformation = userService.getMyInfor();
        return ResponseEntity.ok().body(
                ApiResponse
                        .builder()
                        .message("Get user information successfully")
                        .result(userInformation)
                        .build());
    }

    @GetMapping("/getSignUpRequest/{id}")
    public ResponseEntity<?> getSignUpRequest(@PathVariable Long id) {
        try {
            List<UsersEntity> usersEntityList = userService.findAllSignUpRequest(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(usersEntityList)
                    .message("Get all sign up request successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateRoleId/{email}")
    public ResponseEntity<?> updateRoleId(@PathVariable String email,  @Valid @RequestBody UsersDTO userDto) {
        try {
            UsersEntity userEntity = userService.updateRoleId(email, userDto);
            return ResponseEntity.ok().body(ApiResponse.builder()
                    .message("Accept new signup successfully")
                    .result(userEntity)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllEmployee/{id}")
    public ResponseEntity<?> getAllEmployee(@PathVariable Long id) {
        try {
            List<UsersEntity> usersEntityList = userService.findAllEmployee(id);
            return ResponseEntity.ok(ResponseObject.builder()
                    .data(usersEntityList)
                    .message("Get all employee successfully")
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
