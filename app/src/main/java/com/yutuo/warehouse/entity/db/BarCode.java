package com.yutuo.warehouse.entity.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 条形码扫描后存储的数据
 */
@Entity
public class BarCode {

    @Id(autoincrement = true)
    private Long id;

    private String content;

    @Generated(hash = 405303049)
    public BarCode(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Generated(hash = 303441476)
    public BarCode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
