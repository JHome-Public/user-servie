package com.jhome.user.dto;

import com.jhome.user.common.exception.CustomException;
import com.jhome.user.common.response.ApiResponseCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@ToString
public class SearchRequest {

    @Min(value = 1, message = "Size must be at least 0")
    private Integer page = 0;

    @Min(value = 1, message = "Size must be at least 1")
    private Integer size = 10;

    private String sortBy = "createdAt";

    @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be 'asc' or 'desc'.")
    private String sortDirection = "desc";

    private String searchKeyword;

    public Pageable createPageable() {
        List<String> ALLOWED_SORT_FIELDS = List.of("createdAt", "username", "email");

        if(!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new CustomException(ApiResponseCode.REQUEST_SORT_FIELD_INVALID);
        }

        return PageRequest.of(
                page, size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
    }
}
