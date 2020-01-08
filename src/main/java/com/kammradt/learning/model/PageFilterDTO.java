package com.kammradt.learning.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter @Setter
public class PageFilterDTO {

    private int page = 0;
    private int size = 10;
    private String sort = "";

    public PageFilterDTO(Map<String, String> params) {
        if (params.containsKey("page"))
            this.page = Integer.parseInt(params.get("page"));

        if (params.containsKey("size"))
            this.size = Integer.parseInt(params.get("size"));

        if (params.containsKey("sort"))
            this.sort = params.get("sort") ;
    }

    public PageRequest toPageable() {
        List<Sort.Order> orders = Arrays.stream(this.sort.split(","))
                .filter(prop -> prop.trim().length() > 0)
                .map(this::mapStringToOrder)
                .collect(Collectors.toList());

        return PageRequest.of(this.page, this.size, Sort.by(orders));
    }

    public Sort.Order mapStringToOrder(String column) {
        column = column.trim();
        if (column.startsWith("-"))
            return Sort.Order.desc(column.replace("-", ""));
        return Sort.Order.asc(column);
    }

}
