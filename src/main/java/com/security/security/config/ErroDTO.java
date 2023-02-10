package com.security.security.config;

import lombok.Data;

@Data
public class ErroDTO {
    private int Status;
    private String messae;

    public ErroDTO(){};
    public ErroDTO(int status, String messae) {
        Status = status;
        this.messae = messae;
    }


}
