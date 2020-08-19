package com.mc.bzly.util;

import java.util.Random;

/**
 * 随机瓜分金额工具类
 * @date 2019年3月1日
 * TODO
 */
public class PartitionAmountUtil {

	private static double singleMin = 0.01;//单个红包最小金额
	
	public static double partition(int count, double amount, double singleMax) throws UnsupportedOperationException {
        if (amount > singleMax || amount < singleMin) {
            throw new UnsupportedOperationException(String.format("金额不能大于%s,不能小于%s", singleMax, singleMin));
        }
        if (count * singleMin > amount) {
            throw new UnsupportedOperationException(String.format("金额不足够发%s个红包", count));
        }

        if (count == 1) {
            return (double) Math.round(amount * 100) / 100;//剩余最后一个
        }

        double min = 0.01;//当前红包的最小金额
        double max = amount / count * 2;//当前红包的最大金额
        double money = new Random().nextDouble() * max;//金额
        money = money <= min ? min : Math.floor(money * 100) / 100;//特殊处理,保留2位小数，四舍五入
        return money;
    }
	
	/**
	 * 获取抽奖奖品下标
	 * @param goodsSize
	 * @return
	 */
	public static int getLotteryOrderIndex(int goodsSize){
		int d = (int) (Math.random() * 1000);
		return  d % goodsSize;
	}
}
