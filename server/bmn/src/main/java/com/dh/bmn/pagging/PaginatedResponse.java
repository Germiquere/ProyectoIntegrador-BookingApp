package com.dh.bmn.pagging;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginatedResponse<T> {
    private List<T> content;
    private PaginationData paginationData;

    public PaginatedResponse(List<T> content, PaginationData paginationData) {
        this.content = content;
        this.paginationData = paginationData;
    }

    public List<T> getContent() {
        return content;
    }

    public PaginationData getPaginationData() {
        return paginationData;
    }
}

