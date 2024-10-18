package com.studentapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Address {

    @NotBlank(message = "address must not be blank")
    private String address1;

    private String address2;

    @NotBlank(message = "pincode must not be blank")
    @Pattern(regexp = "(^[0-9]{6}$)")
    private String pincode;

    @NotBlank(message = "city must not be blank")
    @Pattern(regexp = "([a-zA-Z\\s+']{1,80}\\s*)+")
    private String city;

    @NotBlank(message = "state must not be blank")
    @Pattern(regexp = "([a-zA-Z\\s+']{1,80}\\s*)+")
    private String state;
}
