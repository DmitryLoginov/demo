package dev.ldv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private PageableObject pageable;

    public static <T> PageResponse<T> fromPage(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        PageableObject pageableObject = new PageableObject();

        pageableObject.setPageNumber(page.getNumber());
        pageableObject.setPageSize(page.getSize());
        pageableObject.setTotalPages(page.getTotalPages());
        pageableObject.setTotalElements(page.getNumberOfElements());

        pageResponse.setPageable(pageableObject);
        pageResponse.setContent(page.getContent());

        return pageResponse;
    }

    public static <T, R> PageResponse<R> fromPage(Page<T> page, Function<T, R> mappingFunction) {
        PageResponse<R> pageResponse = new PageResponse<>();
        PageableObject pageableObject = new PageableObject();

        pageableObject.setPageNumber(page.getNumber());
        pageableObject.setPageSize(page.getSize());
        pageableObject.setTotalPages(page.getTotalPages());
        pageableObject.setTotalElements(page.getNumberOfElements());

        pageResponse.setPageable(pageableObject);
        pageResponse.setContent(page.getContent().stream().map(mappingFunction).toList());

        return pageResponse;
    }
}