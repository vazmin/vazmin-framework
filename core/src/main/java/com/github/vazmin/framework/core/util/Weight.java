package com.github.vazmin.framework.core.util;

/**
 * 权重随机
 * Created by Chwing on 2017/9/15.
 */
public class Weight<T> {
    // 权重
    private Integer weight;
    //值
    private T target;

    public Weight(){

    }

    public Weight(Integer weight, T target) {
        this.weight = weight;
        this.target = target;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
