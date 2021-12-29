package com.epam.rd.autotasks.springemployeecatalog.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class JsonPage<T> extends PageImpl<T> {
    public JsonPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public JsonPage(List<T> content) {
        super(content);
    }

    @Override
    @JsonIgnore
    public Pageable getPageable() {
        return super.getPageable();
    }

    @Override
    @JsonIgnore
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @Override
    @JsonIgnore
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @Override
    @JsonIgnore
    public int getNumber() {
        return super.getNumber();
    }

    @Override
    @JsonIgnore
    public int getSize() {
        return super.getSize();
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return super.getSort();
    }

    @Override
    @JsonIgnore
    public boolean isLast() {
        return super.isLast();
    }

    @Override
    @JsonIgnore
    public int getNumberOfElements() {
        return super.getNumberOfElements();
    }

    @Override
    @JsonIgnore
    public boolean isFirst() {
        return super.isFirst();
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    @JsonIgnore
    public List<T> getContent() {
        return super.getContent();
    }
}