package com.tencent.wxcloudrun.cons;

import lombok.Getter;


public enum DeviceCategory {


    Category_01("CT类"),
    Category_02("磁共振MRI类"),
    Category_03("血管造影机DSA类"),
    Category_04("普放类"),
    Category_05("核医学类"),
    Category_06("超声影像类"),
    Category_07("生命体征诊断类"),
    Category_08("监护类"),
    Category_09("软式内窥镜及主机系统"),
    Category_10("硬式内窥镜及主机系统"),
    Category_11("检验及病理类"),
    Category_12("热成像类"),
    Category_13("显微镜类"),
    Category_14("体外循环类"),
    Category_15("血液净化类"),
    Category_16("医用刀类"),
    Category_17("输液注射类"),
    Category_18("康复类"),
    Category_19("灯床塔等手术室设备类"),
    Category_20("医用激光类"),
    Category_21("除颤类"),
    Category_22("麻醉类"),
    Category_23("呼吸类"),
    Category_24("放疗类"),
    Category_25("消毒灭菌类"),
    Category_26("供氧系统"),
    Category_27("制冷及空调设备类"),
    Category_28("制药配药机械装备"),
    Category_29("超高值其它类"),
    Category_30("高值其它类"),
    Category_31("低值其它类"),
    Category_32("非医疗设备类"),
    ;

    @Getter
    private String categoryName;

    private  DeviceCategory(String categoryName) {
        this.categoryName = categoryName;
    }

}
