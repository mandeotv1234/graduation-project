package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetPostStatsResponse;

public record GetPostStatsResponseDto(
        long totalViews,
        long thisWeekViews,
        long bestDayViews,
        double averageDaily
) {
    public static GetPostStatsResponseDto fromResponse(GetPostStatsResponse response) {
        return new GetPostStatsResponseDto(
                response.totalViews(),
                response.thisWeekViews(),
                response.bestDayViews(),
                response.averageDaily()
        );
    }
}
