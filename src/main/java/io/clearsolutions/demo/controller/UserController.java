package io.clearsolutions.demo.controller;

import com.github.fge.jsonpatch.JsonPatch;
import io.clearsolutions.demo.annotation.ValidatedDateRange;
import io.clearsolutions.demo.dto.DateRangeDto;
import io.clearsolutions.demo.dto.UserRequestDto;
import io.clearsolutions.demo.dto.UserResponseDto;
import io.clearsolutions.demo.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAllUsersInDateRange(@Valid @ValidatedDateRange DateRangeDto dateRange) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.getAllUsersInDateRange(dateRange.getFrom(), dateRange.getTo()));
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> processUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.processUser(userRequestDto));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<UserResponseDto> updateSomeUserFields(@RequestBody JsonPatch patch, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updateSomeUserFields(patch, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateAllUserFields(@Valid @RequestBody UserRequestDto userRequestDto,
                                                               @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.updateAllUserFields(userRequestDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }
}