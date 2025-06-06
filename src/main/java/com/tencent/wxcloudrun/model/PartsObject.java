package com.tencent.wxcloudrun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PartsObject {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String deviceName;
    private String deviceCategory;
    private String deviceBrand;
    private String devicePattern;
    private String type;  //MSA Prob tube parts

    private String probPattern;
    private String tubePattern;
    private String partsPattern;

    private Date updateTime;
    private Date createTime;
    private String remark;
    private String index_str;
}
