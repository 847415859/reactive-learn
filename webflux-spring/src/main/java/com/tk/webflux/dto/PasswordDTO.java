package com.tk.webflux.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description:
 * @Date : 2023/04/12 16:44
 * @Auther : tiankun
 */
@Data
public class PasswordDTO {

    private String raw;
    private String secured;

    @JsonCreator
    public PasswordDTO(@JsonProperty("raw") String raw,
                       @JsonProperty("secured") String secured) {
        this.raw = raw;
        this.secured = secured;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getSecured() {
        return secured;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }
}
