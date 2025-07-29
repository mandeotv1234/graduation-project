package graduation_project.user_service.adapter.web.api.dtos.response;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class  PaginationResponseDto<T> {
    private final List<T> data;
    private final PaginationMetaDto pagination;
    private final Meta meta;

    private PaginationResponseDto(List<T> data, int page, int size, long total) {
        this.data = data;
        this.pagination = new PaginationMetaDto(page, size, total);
        this.meta = new Meta();
    }

    public static <T> PaginationResponseDto<T> valueOf(
            List<T> data,
            PaginationMetaDto pagination) {
        return new PaginationResponseDto<>(data, pagination.getPage(), pagination.getSize(), pagination.getTotal());
    }

    @Getter
    public static class Meta {
        private final Date timestamp;

        Meta() {
            timestamp = new Date();
        }
    }

}
