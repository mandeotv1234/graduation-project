package graduation_project.user_service.adapter.web.api.controller;

import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.request
        .EditPostRequestDto;
import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.request.UpdateUserSettingRequestDto;
import com.mgmtp.ihcmc.hive.server.adapter.web.api.dtos.response.*;
import com.mgmtp.ihcmc.hive.server.application.usecases.*;
import com.mgmtp.ihcmc.hive.server.application.usecases.request.EditPostRequest;
import com.mgmtp.ihcmc.hive.server.application.usecases.request.UpdateUserSettingRequest;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.*;
import com.mgmtp.ihcmc.hive.server.domain.models.GetUserPostsFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class CurrentUserController {

    private final GetCurrentUserUsecase getCurrentUserUsecase;
    private final GetUserPostsUsecase getUserPostsUsecase;
    private final GetFollowingUsersUsecase getFollowingUsersUsecase;
    private final GetFollowersUsersUseCase getFollowersUseCase;
    private final FollowUsecase followUsecase;
    private final UnfollowUsecase unfollowUsecase;
    private final GetReadingHistoryUsecase getReadingHistoryUsecase;
    private final GetBookmarkedPostsUsecase getBookmarkedPostsUsecase;
    private final GetRecommendedFollowsUsecase getRecommendedFollowsUsecase;
    private final GetRecommendedTagsUsecase getRecommendedTagsUsecase;
    private final DeletePostUsecase deletePostUsecase;
    private final EditPostUsecase editPostUsecase;
    private final GetUserSettingUsecase getUserSettingUsecase;
    private final UpdateUserSettingUseCase updateUserSettingUseCase;

    @GetMapping
    public ResponseEntity<ResponseDto> getCurrentUser() {
        UserResponseDto response = UserResponseDto.fromModel(getCurrentUserUsecase.execute());
        return ResponseEntity.ok(new ResponseDto(response));
    }

    @PostMapping("/following/{contributorId}")
    public ResponseEntity<ResponseDto> followUser(@PathVariable Long contributorId) {
        FollowResponse response = followUsecase.execute(contributorId);
        FollowResponseDto responseDto = FollowResponseDto.from(response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(responseDto));
    }

    @DeleteMapping("/following/{contributorId}")
    public ResponseEntity<ResponseDto> unfollowUser(@PathVariable Long contributorId) {
        UnfollowResponse response = unfollowUsecase.execute(contributorId);
        UnfollowResponseDto responseDto = UnfollowResponseDto.from(response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(responseDto));
    }

    @GetMapping("/posts/history")
    public ResponseEntity<PaginationResponseDto<ReadHistoryResponseDto>> getHistory(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        PaginationResponse<ReadHistoryResponse> records = getReadingHistoryUsecase.execute(page, size);
        return ResponseEntity.ok().body(
                PaginationResponseDto.valueOf(
                        records.data().stream().map(ReadHistoryResponseDto::from).toList(),
                        PaginationMetaDto.fromResponse(records.pagination())
                )
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<PaginationResponseDto<GetUserPostsResponseDto>> getMyPosts(
            @RequestParam(defaultValue = "published") String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "time", name = "sort-by") String sortBy,
            @RequestParam(defaultValue = "desc", name = "sort-order") String sortOrder
    ) {
        GetUserPostsFilterCriteria criteria = new GetUserPostsFilterCriteria(search, status, sortBy, sortOrder);
        PaginationResponse<GetUserPostsResponse> paginationPosts = getUserPostsUsecase.execute(
                null,
                page,
                size,
                criteria);
        List<GetUserPostsResponseDto> data = GetUserPostsResponseDto.fromResponses(paginationPosts.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationPosts.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/following")
    public ResponseEntity<PaginationResponseDto<GetUsersResponseDto>> getFollowingUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<GetUsersResponse> paginationUsers = getFollowingUsersUsecase.execute(page, size);
        List<GetUsersResponseDto> response = GetUsersResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(response, paginationMeta));
    }

    @GetMapping("/followers")
    public ResponseEntity<PaginationResponseDto<GetUsersWithFollowedFlagResponseDto>> getFollowersUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<GetUserResponseWithFollowedFlag> paginationUsers = getFollowersUseCase.execute(page, size);
        List<GetUsersWithFollowedFlagResponseDto> data = GetUsersWithFollowedFlagResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<PaginationResponseDto<GetBookmarkedPostResponseDto>> getBookmarkedPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<GetBookmarkPostResponse> paginationPosts = getBookmarkedPostsUsecase.execute(page, size);
        List<GetBookmarkedPostResponseDto> data = GetBookmarkedPostResponseDto.fromResponses(paginationPosts.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationPosts.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/recommended-followings")
    public ResponseEntity<PaginationResponseDto<GetRecommendedFollowResponseDto>> getRecommendedFollows(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<GetRecommendedFollowResponse> paginationUsers = getRecommendedFollowsUsecase.execute(page, size);
        List<GetRecommendedFollowResponseDto> data = GetRecommendedFollowResponseDto.fromResponses(paginationUsers.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(paginationUsers.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @GetMapping("/recommended-tags")
    public ResponseEntity<PaginationResponseDto<TagResponseDto>> getRecommendedTags(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginationResponse<TagResponse> response = getRecommendedTagsUsecase.execute(page, size);
        List<TagResponseDto> data = TagResponseDto.fromResponses(response.data());
        PaginationMetaDto paginationMeta = PaginationMetaDto.fromResponse(response.pagination());
        return ResponseEntity.status(HttpStatus.OK).body(PaginationResponseDto.valueOf(data, paginationMeta));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long postId) {
        DeletePostResponse response = deletePostUsecase.execute(postId);
        DeletePostResponseDto responseDto = DeletePostResponseDto.from(response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(responseDto));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseDto> editPost(
            @PathVariable Long postId,
            @RequestBody EditPostRequestDto requestDto) {
        EditPostRequest request = requestDto.toRequest();
        EditPostResponse response = editPostUsecase.execute(postId, request);
        EditPostResponseDto responseDto = EditPostResponseDto.from(response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(responseDto));
    }

    @GetMapping("/settings")
    public ResponseEntity<ResponseDto> getUserSetting(
            @RequestParam(name="key") String key
    ) {
        GetUserSettingResponse response = getUserSettingUsecase.execute(key);
        GetUserSettingResponseDto responseDto = GetUserSettingResponseDto.fromResponse(response);
        return ResponseEntity.ok(new ResponseDto(responseDto));
    }

    @PutMapping("/settings")
    public ResponseEntity<ResponseDto> updateUserSetting(
            @RequestBody UpdateUserSettingRequestDto updateUserSettingRequestDto,
            @RequestParam(name="key") String key
    ) {
        UpdateUserSettingRequest request = updateUserSettingRequestDto.toRequest();
        UpdateUserSettingResponse response = updateUserSettingUseCase.execute(request, key);
        UpdateUserSettingResponseDto responseDto = UpdateUserSettingResponseDto.fromResponse(response);
        return ResponseEntity.ok(new ResponseDto(responseDto));
    }
}
