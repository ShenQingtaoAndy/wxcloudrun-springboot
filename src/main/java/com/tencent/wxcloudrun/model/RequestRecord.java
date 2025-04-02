package com.tencent.wxcloudrun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RequestRecord {


    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String partsId;
    private String hospitalName;
    private String requestorId;
    private String status;
    private String remark;
    private Date updateTime;
    private Date createTime;
    private String responserId;


    private String hasIssue;  //MSA 是否带病入保（如是，需弹屏，提示填写坏的部件名称）   如是，需弹屏，提示填写坏的部件名称
    private String hasCompetition;  //MSA 是否有原厂在竞争
    private String requestType;  //MSA 询价类型：原厂 or 三方（可选项）

    private String resAllInsurance;  //MSA 全保（含球管/探测器）
    private String resAllInsuranceExceptTube;  //MSA 全保不含球管不含探测器  转包成交价 万RMB
    private String resManualInsurance;  //MSA 人工保（技术保）
    private String resPerDispatch;  //MSA 单次派工(w)


    private String resNewBuyOut;  //!MSA 全新买断（W）
    private String resSecondBuyOut;  //!MSA 二手买断(W)
    private String resNewSwap;  //!MSA 全新交换(W)
    private String resSecondSwap;  //!MSA 二手交换(W)
    private String resMaintain;  //!MSA 二手交换(W)
    private String resWarrantyPeriod;  //!MSA 质保期




    @ManyToOne(fetch = FetchType.EAGER )
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "partsId",  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = true,insertable=false, updatable=false)
    private PartsObject partsObject;



    @ManyToOne(fetch = FetchType.EAGER )
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "requestorId",  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = true,insertable=false, updatable=false)
    private User requestor;


    @ManyToOne(fetch = FetchType.EAGER )
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "responserId",  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = true,insertable=false, updatable=false)
    private User responser;
}
