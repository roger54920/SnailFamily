package com.example.ysww.snailfamily.bean.shopping;

import com.example.ysww.snailfamily.bean.BaseBean;

import java.util.List;

/**
 * Created by ysww on 2017/10/17.
 * 热门搜索
 */

public class CommonSearchBean extends BaseBean {

    private List<String> commonSearchList;

    public List<String> getCommonSearchList() {
        return commonSearchList;
    }

    public void setCommonSearchList(List<String> commonSearchList) {
        this.commonSearchList = commonSearchList;
    }
}
