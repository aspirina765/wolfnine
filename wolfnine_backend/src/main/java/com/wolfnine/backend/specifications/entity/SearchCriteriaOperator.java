package com.t2009m1.spring_ecommerce.entity.search;

public enum SearchCriteriaOperator {
    EQUALS, NOT_EQUALS,
    GREATER_THAN, GREATER_THAN_OR_EQUALS,
    LESS_THAN, LESS_THAN_OR_EQUALS,
    LIKE,
    IN,
    JOIN;
}
