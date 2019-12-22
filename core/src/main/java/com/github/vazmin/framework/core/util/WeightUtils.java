package com.github.vazmin.framework.core.util;

import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 权重工具类
 * Created by Chwing on 2017/9/15.
 */
public class WeightUtils {

    /**
     * 根据权重随机获取对应值
     * @param jsonData 权重随机对象列表json
     * @return T 值
     */
    public static <T> T next(String jsonData){
        List<Weight<T>> weightRandomList =
                GsonUtils.fromJson(jsonData,
                        new TypeToken<List<Weight<T>>>(){}.getType());
        return next(weightRandomList);
    }

    /**
     * 根据权重随机对象列表获取随机值
     * @param weightRandomList 权重随机对象列表
     * @return V 值
     */
    public static <V> V next(List<Weight<V>> weightRandomList) {
        int sum = getWeightSum(weightRandomList);    // 权重总和
        // 如果设置的数落在随机数内，则返回，否则减去本次的数
        for (Weight<V> weightRandom : weightRandomList) {
            if (weightRandom.getWeight() >= Math.floor(Math.random() * sum + 1)) {
                return weightRandom.getTarget();
            } else {
                sum -= weightRandom.getWeight();
            }
        }
        return null;
    }

    /**
     * 根据权重随机对象列表获取随机值
     * @param weightList 权重随机对象列表
     * @return V 值
     */
    public static <V> V random(List<Weight<V>> weightList) {
        // Number of Weight
        int length = weightList.size();
        // Has the same weight?
        boolean sameWeight = true;
        // the weight of every one
        int[] weights = new int[length];
        // the first Weight's weight
        int firstWeight = weightList.get(0).getWeight();
        weights[0] = firstWeight;
        // The sum of weights
        int totalWeight = firstWeight;
        for (int i = 1; i < length; i++) {
            int weight = weightList.get(i).getWeight();
            // save for later use
            weights[i] = weight;
            // Sum
            totalWeight += weight;
            if (sameWeight && weight != firstWeight) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            // If (not every Weight has the same weight & at least one Weight's weight>0), select randomly based on totalWeight.
            int offset = ThreadLocalRandom.current().nextInt(totalWeight);
            // Return a Weight based on the random value.
            for (int i = 0; i < length; i++) {
                offset -= weights[i];
                if (offset < 0) {
                    return weightList.get(i).getTarget();
                }
            }
        }
        // If all Weight have the same weight value or totalWeight=0, return evenly.
        return weightList.get(ThreadLocalRandom.current().nextInt(length)).getTarget();
    }

    /**
     * 权重总和
     * @param weightRandomList 权重随机对象列表
     * @param <V> 值类型
     * @return int
     */
    private static <V> int getWeightSum(List<Weight<V>> weightRandomList){
        int sum = 0;
        for (Weight<V> weightRandom : weightRandomList) {
            sum += weightRandom.getWeight();
        }
        return sum;
    }

}
