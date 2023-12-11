package com.prior.inbox.model;

import lombok.Data;


@Data
public class ResponseModel<E>{
    private int status;
    private String description;
    private E data;
}