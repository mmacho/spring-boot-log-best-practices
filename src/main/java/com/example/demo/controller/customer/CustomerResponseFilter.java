package com.example.demo.controller.customer;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.example.demo.controller.BaseResponse;

@JsonFilter(BaseResponse.FIELDS_FILTER)
public class CustomerResponseFilter extends CustomerResponse {

    private static final long serialVersionUID = -8450793661497187180L;

}
