package com.emp.ems.common.util;

import com.emp.ems.common.dto.PageResponse;
import org.springframework.data.domain.Page;

public final class PageUtils {

    public PageUtils() {
    }

    public static <T> PageResponse<T> convertToPageResponse(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}