package org.example.loyalty.loyalty.application.model;

import lombok.Getter;

@Getter
public class PagingFilterRequest {
    private static final int MAX = 200;
    protected int page;
    protected int limit = 10;

    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public void setLimit(int limit) {
        if (limit < 1) {
            limit = 1;
        }
        if (limit > MAX) {
            limit = MAX;
        }

        this.limit = limit;
    }
}
