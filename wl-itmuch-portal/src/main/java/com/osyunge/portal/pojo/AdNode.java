package com.osyunge.portal.pojo;

import lombok.Data;

/**
 * 图片路径及内容封装类
 * Created by 胡金才
 * 2019/6/18 9:26
 */
@Data
public class AdNode {
    private int height;
    private int width;
    private String src;
    private int heightB;
    private int widthB;
    private String srcB;
    private String alt;
    private String href;
}
