package com.dh.bmn.pagging;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class PaginatedResponse<T> {
    private List<T> content;
    private PaginationData paginationData;

    public PaginatedResponse(List<T> content, PaginationData paginationData) {
        this.content = content;
        this.paginationData = paginationData;
    }
}

