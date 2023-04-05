package com.tk.earlyspringimplement.entity;

import lombok.Data;

/**
 * @Description:
 * @Date : 2023/04/05 8:02
 * @Auther : tiankun
 */
@Data
public class Temperature {

    // 温度
    private double value;

    public Temperature() {
    }

    public Temperature(double value) {
        this.value = value;
    }
}
