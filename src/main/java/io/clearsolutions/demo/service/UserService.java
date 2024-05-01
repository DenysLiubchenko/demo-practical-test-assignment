package io.clearsolutions.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.clearsolutions.demo.dto.UserRequestDto;
import io.clearsolutions.demo.dto.UserResponseDto;
import io.clearsolutions.demo.dto.UserVO;
import io.clearsolutions.demo.exception.exception.PatchException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static io.clearsolutions.demo.constant.ExceptionMessage.USER_PATCH_EXCEPTION;

@Service
@AllArgsConstructor
public class UserService {
    private ObjectMapper objectMapper;

    /**
     * Updates selected fields of a user based on the provided JSON Patch and user ID.
     *
     * @param userPatchRequestDto The JSON Patch containing the fields to update.
     * @param userId              The ID of the user to update.
     * @return The updated user response DTO.
     * @throws PatchException If there's an error applying the patch.
     */
    public UserResponseDto updateSomeUserFields(JsonPatch userPatchRequestDto, Long userId) {
        // TODO: Get user fields from database by userId
        // User sample object
        UserVO user =
            new UserVO(1L,"emailsample@test.com", "FirstName", "LastName",
                LocalDate.of(1990, 1, 1), "testAddress",
                "1234567890");
        UserVO patchedUser;
        try {
            patchedUser = applyPatch(userPatchRequestDto, user);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new PatchException(USER_PATCH_EXCEPTION + e);
        }
        // TODO: Save updated user
        return objectMapper.convertValue(patchedUser,UserResponseDto.class);
    }

    /**
     * Retrieves all users within the specified date range.
     *
     * @param from The start date of the range.
     * @param to   The end date of the range.
     * @return A list of user response DTOs within the date range.
     */
    public List<UserResponseDto> getAllUsersInDateRange(LocalDate from, LocalDate to) {
        // TODO: Find all users in data range
        return new ArrayList<>();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUserById(Long id) {
        // TODO: Delete user from database
    }

    /**
     * Updates all fields of a user based on the provided user request DTO and user ID.
     *
     * @param userRequestDto The user request DTO containing updated fields.
     * @param userId         The ID of the user to update.
     * @return The updated user response DTO.
     */
    public UserResponseDto updateAllUserFields(UserRequestDto userRequestDto, Long userId) {
        // TODO: Save updated user
        return new UserResponseDto(1L,"emailsample@test.com", "FirstName", "LastName",
            LocalDate.of(1990, 1, 1), "testAddress",
            "1234567890");
    }

    /**
     * Processes a user request by creating a new user and saving them to the database.
     *
     * @param userRequestDto The user request DTO containing user information.
     * @return The response DTO of the created user.
     */
    public UserResponseDto processUser(UserRequestDto userRequestDto) {
        // TODO: Create new user and save him to database
        return new UserResponseDto(1L,"emailsample@test.com", "FirstName", "LastName",
            LocalDate.of(1990, 1, 1), "testAddress",
            "1234567890");
    }

    private UserVO applyPatch(JsonPatch patch, UserVO targetUser)
        throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, UserVO.class);
    }
}
