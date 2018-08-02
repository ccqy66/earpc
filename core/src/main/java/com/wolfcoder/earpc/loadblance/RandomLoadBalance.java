package com.wolfcoder.earpc.loadblance;

import com.wolfcoder.earpc.model.RemoteServer;

import java.util.List;
import java.util.Random;

/**
 * @author: chenchen_839@126.com
 * @date: 2018/2/9
 */
public class RandomLoadBalance implements ILoadBalance{
    private static final Random random = new Random();
    @Override
    public RemoteServer select(List<RemoteServer> serverList) {
        if (serverList.size() == 0)
            return null;
        // 总个数
        int length = serverList.size();
        double[] weightAccumulate = new double[length];
        // 总权重
        double totalWeight = 0;
        // 权重是否都一样
        boolean sameWeight = true;
        double lastWeight = -1;
        for (int i = 0; i < length; i++) {
            // 获取权重
            double weight = serverList.get(i).getWeight();
            // 累计总权重
            totalWeight += weight;
            weightAccumulate[i] = totalWeight;
            // 判断所有权重是否一样
            if (sameWeight && i > 0 && Double.compare(weight, lastWeight) != 0) {
                sameWeight = false;
            }
            lastWeight = weight;
        }
        if (!sameWeight && Double.compare(totalWeight, 0) > 0) {
            // 如果权重不相同且权重大于0则按总权重数随机
            double offset = random.nextDouble() * totalWeight;
            // 并确定随机值落在哪个片断上
            for (int i = 0; i < length; i++) {
                double weightA = weightAccumulate[i];
                if (Double.compare(offset, weightA) < 0) {
                    return serverList.get(i);
                }
            }
        }
        // 如果权重相同或权重为10则均等随机
        return serverList.get(random.nextInt(length));
    }
}
