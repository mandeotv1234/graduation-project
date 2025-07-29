package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.CreateTagsRequest;

import java.util.List;

public record CreateTagsRequestDto(List<String> titles) {

    public CreateTagsRequest toRequest() {
        return new CreateTagsRequest(titles);
    }
}
