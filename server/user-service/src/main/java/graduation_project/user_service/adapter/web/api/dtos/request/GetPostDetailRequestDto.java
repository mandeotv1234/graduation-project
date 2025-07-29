
package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.GetPostDetailRequest;

public record GetPostDetailRequestDto(Long id) {
    public GetPostDetailRequest toRequest() {
        return new GetPostDetailRequest(id);
    }
}