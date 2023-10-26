package com.dh.bmn.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PaginationData {
    private long total;
    private int primary_results;
    private long offset;
    private int limit;
}

