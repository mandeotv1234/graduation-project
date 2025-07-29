package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.ReadHistoryResponse;

import java.util.Date;


public record ReadHistoryResponseDto(GetPostsResponseDto post, Date lastViewed, Double progress, Integer readCount) {
    public static ReadHistoryResponseDto from(ReadHistoryResponse resp){
        return new ReadHistoryResponseDto(GetPostsResponseDto.fromResponse(resp.post()), resp.lastViewed(),  resp.progress(), resp.readCount());
    }
}
