package io.wabm.supermarket.misc.enums;

/**
 * Created by MainasuK on 2016-12-11.
 */
public enum OrderStatusEnum {
    unknown, pending, transporting, done;

    @Override
    public String toString() {
        switch (this) {
            case unknown: return "未知";
            case pending: return "待审核";
            case transporting: return "待收货";
            case done: return "已收货";
            default: return "不可达状态";
        }
    }
}
