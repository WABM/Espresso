package io.wabm.supermarket.misc.enums;

/**
 * Created by MainasuK on 2016-12-13.
 */
public enum StorageChangeEnum {
    unknown, prifit, lose, reject;

    @Override
    public String toString() {
        switch (this) {
            case unknown: return "未知";
            case prifit: return "盘盈";
            case lose: return "盘亏";
            case reject: return "报废";
            default: return "不可达状态";
        }
    }
}
