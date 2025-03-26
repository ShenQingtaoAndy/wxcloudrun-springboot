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

    @ManyToOne(fetch = FetchType.EAGER )
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "partsId",  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = true,insertable=false, updatable=false)
    private PartsObject partsObject;



    @ManyToOne(fetch = FetchType.EAGER )
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "requestorId",  foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = true,insertable=false, updatable=false)
    private User requestor;
}
