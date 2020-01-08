package com.kammradt.learning.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

@Getter @Setter
public class PageFilterDTO {

    private int page = 0;
    private int size = 10;

    public PageFilterDTO(Map<String, String> params) {
        if (params.containsKey("page"))
            this.page = Integer.parseInt(params.get("page"));

        if (params.containsKey("size"))
            this.size = Integer.parseInt(params.get("size"));
    }

    public PageRequest toPageable() {
        return PageRequest.of(this.page, this.size);
    }

}
