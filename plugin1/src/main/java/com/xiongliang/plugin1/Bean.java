package com.xiongliang.plugin1;

import com.xiongliang.pluginlibrary.IBean;

public class Bean implements IBean {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
