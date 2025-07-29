package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.CreateTagsResponse;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.TagResponse;

import java.util.List;

public record TagResponseDto(Long id, String title) {
    public static TagResponseDto from(TagResponse tagResponse){
        return new TagResponseDto(tagResponse.id(), tagResponse.title());
    }

    public static List<TagResponseDto> fromResponses(CreateTagsResponse response) {
        return response.tags().stream()
                .map(tag -> new TagResponseDto(tag.id(), tag.title()))
                .toList();
    }

    public static List<TagResponseDto> fromResponses(List<TagResponse> tagResponses) {
        return tagResponses.stream().map(TagResponseDto::from).toList();
    }
}
