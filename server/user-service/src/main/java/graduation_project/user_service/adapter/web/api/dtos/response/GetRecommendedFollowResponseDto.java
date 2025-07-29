package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetRecommendedFollowResponse;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Role;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Status;

import java.util.List;

public record GetRecommendedFollowResponseDto(
        Long id,
        String email,
        Status status,
        Role role,
        String fullName,
        String imageUrl,
        String department,
        String bio,
        String about
) {

    public static GetRecommendedFollowResponseDto fromResponse(final GetRecommendedFollowResponse getUsersResponse) {
        return new GetRecommendedFollowResponseDto(
                getUsersResponse.id(),
                getUsersResponse.email(),
                getUsersResponse.status(),
                getUsersResponse.role(),
                getUsersResponse.fullName(),
                getUsersResponse.imageUrl(),
                getUsersResponse.department(),
                getUsersResponse.bio(),
                getUsersResponse.about());
    }

    public static List<GetRecommendedFollowResponseDto> fromResponses(List<GetRecommendedFollowResponse> userResponses) {
        return userResponses.stream().map(GetRecommendedFollowResponseDto::fromResponse).toList();
    }
}

