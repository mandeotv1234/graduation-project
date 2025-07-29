package graduation_project.user_service.adapter.web.api.controller;

import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.request.UpdateRoleStatusRequestDto;
import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.request.UpdateUserRequestDto;
import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.request.UserRequestDto;
import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.response.*;
import com.mgmtp.ihcmc.hive.server.application.usecases.*;
import com.mgmtp.ihcmc.hive.server.application.usecases.ChangeRoleStatusUseCase;
import com.mgmtp.ihcmc.hive.server.application.usecases.RegisterUserUsecase;
import com.mgmtp.ihcmc.hive.server.application.usecases.UserGetUserByIdUseCase;
import com.mgmtp.ihcmc.hive.server.application.usecases.request.GetUsersRequest;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.*;
import com.mgmtp.ihcmc.hive.server.domain.models.GetUserPostsFilterCriteria;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUsecase registerUserUsecase;
    private final UpdateUserUsecase updateUserUsecase;

    private final UserGetUserByIdUseCase userGetUserByIdUseCase;
    private final ChangeRoleStatusUseCase changeRoleStatusUseCase;
    private final GetUsersUsecase getUsersUsecase;
    private final SearchUsersUsecase searchUsersUsecase;

    private final GetUserPostsUsecase getUserPostsUsecase;
    private final GetFollowingUsersUsecase getFollowingUsersUsecase;
    private final GetFollowersUsersUseCase getFollowersUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto response = UserResponseDto.from(registerUserUsecase.execute(userRequestDto.toRequest()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getUserById(@PathVariable Long id) {
        GetUsersWithFollowedFlagResponseDto response = GetUsersWithFollowedFlagResponseDto.fromResponse(userGetUserByIdUseCase.execute(id));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateRoleStatusRequestDto updateRoleStatusRequestDto) {
        UserResponseDto response = UserResponseDto.fromModel(changeRoleStatusUseCase.execute(id, updateRoleStatusRequestDto.toModel()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(response));
    }

    @PatchMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto> updateCurrentUser(
            @RequestPart("user") UpdateUserRequestDto userRequestDto,
            @RequestPart(value = "avatar", required = false) MultipartFile multipartFile
    ) {

        UpdateUserRequestDto updatedUserRequestDto;
        updatedUserRequestDto = new UpdateUserRequestDto(
                userRequestDto.fullName(),
                userRequestDto.department(),
                userRequestDto.bio(),
                multipartFile,
                userRequestDto.about()
        );
        UserResponseDto response = UserResponseDto.fromModel(updateUserUsecase.execute(updatedUserRequestDto.toRequest()));
        return ResponseEntity.ok(new ResponseDto(response));
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDto<GetUsersResponseDto>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id", name = "sort-by") String sortBy,
            @RequestParam(defaultValue = "asc", name = "sort-order") String sortOrder,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer following
    ) {
        GetUsersRequest getUsersRequest = new GetUsersRequest(search, role, status, following);
        PaginationResponse<GetUsersResponse> paginationUsers = getUsersUsecase.execute(getUsersRequest, page, size, sortBy, sortOrder);

        List<GetUsersResponseDto> data = GetUsersResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());

        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/search")
    public ResponseEntity<PaginationResponseDto<GetUsersWithFollowedFlagResponseDto>> searchUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false, name = "is-author") boolean isAuthor,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        PaginationResponse<GetUserResponseWithFollowedFlag> paginationUsers = searchUsersUsecase.execute(search, isAuthor,  page, size);
        List<GetUsersWithFollowedFlagResponseDto> data = GetUsersWithFollowedFlagResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());

        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<PaginationResponseDto<GetUsersWithFollowedFlagResponseDto>> getUserFollowing(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<GetUserResponseWithFollowedFlag> paginationUsers = getFollowingUsersUsecase.execute(userId, page, size);
        List<GetUsersWithFollowedFlagResponseDto> data = GetUsersWithFollowedFlagResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());
        return ResponseEntity.ok(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<PaginationResponseDto<GetUsersWithFollowedFlagResponseDto>> getUserFollower(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<GetUserResponseWithFollowedFlag> paginationUsers = getFollowersUseCase.execute(userId, page, size);
        List<GetUsersWithFollowedFlagResponseDto> data = GetUsersWithFollowedFlagResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());
        return ResponseEntity.ok(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<PaginationResponseDto<GetUserPostsResponseDto>> getUserPost(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String search
    ) {
        GetUserPostsFilterCriteria criteria = new GetUserPostsFilterCriteria(search);
        PaginationResponse<GetUserPostsResponse> paginationPosts = getUserPostsUsecase.execute(userId,page,size,criteria);
        List<GetUserPostsResponseDto> data = GetUserPostsResponseDto.fromResponses(paginationPosts.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationPosts.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }
}