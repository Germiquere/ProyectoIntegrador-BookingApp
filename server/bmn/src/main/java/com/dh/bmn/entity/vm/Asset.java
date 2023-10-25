package com.dh.bmn.entity.vm;

import lombok.Builder;
import lombok.Value;
import java.net.URL;

@Value
@Builder
public class Asset {
    String key;
    URL url;
}

