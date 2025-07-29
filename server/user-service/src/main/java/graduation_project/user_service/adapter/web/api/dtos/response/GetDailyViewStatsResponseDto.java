package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetDailyViewStatsResponse;
import java.time.LocalDate;
import java.util.List;

public record GetDailyViewStatsResponseDto(
        List<DailyViewDataDto> dailyViews,
        String period,
        long totalViews
) {
    public record DailyViewDataDto(
            LocalDate date,
            long views
    ) {}

    public static GetDailyViewStatsResponseDto fromResponse(GetDailyViewStatsResponse response) {
        List<DailyViewDataDto> dailyViewsDto = response.dailyViews().stream()
                .map(data -> new DailyViewDataDto(data.date(), data.views()))
                .toList();

        return new GetDailyViewStatsResponseDto(
                dailyViewsDto,
                response.period(),
                response.totalViews()
        );
    }
}