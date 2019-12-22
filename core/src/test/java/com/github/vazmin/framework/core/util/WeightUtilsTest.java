package com.github.vazmin.framework.core.util;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Chwing on 2019/12/22.
 */
class WeightUtilsTest {

    @Test
    public void foo() {
        // A : B : C = 1 : 3 : 6
        String jsonData = "[{\"weight\":10,\"target\": 0},{\"weight\":30,\"target\": 1},{\"weight\":60,\"target\": 2}]";

        List<Weight<Integer>> weightList =
                GsonUtils.fromJson(jsonData,
                        new TypeToken<List<Weight<Integer>>>(){}.getType());
        Integer [] randomSum = {0, 0, 0};
        Integer [] doSum = {0, 0, 0};
        for(int i = 0; i < 10000; i ++){
            randomSum[WeightUtils.next(weightList)] ++;
            doSum[WeightUtils.random(weightList)] ++;
        }

        System.out.println(randomSum[0] + ";" + randomSum[1] + ";" + randomSum[2]);
        System.out.println(doSum[0] + ";" + doSum[1] + ";" + doSum[2]);
    }

    @Test
    public void blog() {
        r();
    }

    public char r() {
        char[] elements = new char[]{'a', 'b', 'c'};
        // select randomly based on totalWeight.
        int[] weights = new int[]{1, 3, 6};
        int totalWeight = Arrays.stream(weights).sum();
        int offset = ThreadLocalRandom.current()
                .nextInt(totalWeight);
        // Return a Weight based on the random value.
        for (int i = 0; i < weights.length; i++) {
            offset -= weights[i];
            if (offset < 0) {
                System.out.println("random target: " + elements[i]);
            }
        }
        return elements[ThreadLocalRandom.current()
                .nextInt(totalWeight)];
    }
}