package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.PaginationResponse;
import lombok.Getter;

@Getter
public class PaginationMetaDto {
    private final int page;
    private final int size;
    private final long total;
    private final int totalPages;

    public PaginationMetaDto(int page, int size, long total) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
    }

    public static PaginationMetaDto fromResponse(PaginationResponse.PaginationMeta paginationMeta) {
        return new PaginationMetaDto(
                paginationMeta.getPage(),
                paginationMeta.getSize(),
                paginationMeta.getTotal());
    }
}
