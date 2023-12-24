package com.example.chatserver.utils;

import java.util.Random;
import java.util.UUID;
public class UuidUtil {

        public static void main(String[] args) {
            // 生成19位纯数字的UUID
            long numericUUID = generateNumericUUID(19);

            System.out.println("生成的19位纯数字的UUID as Long: " + numericUUID);
        }

        // 生成指定位数的纯数字UUID并返回其long表示形式
        public static long generateNumericUUID(int length) {
            Random random = new Random();
            StringBuilder numericUUIDStr = new StringBuilder(length);

            // 确保第一位不是0
            numericUUIDStr.append((char)('1' + random.nextInt(9)));

            // 生成剩余的数字
            for (int i = 1; i < length; i++) {
                numericUUIDStr.append((char)('0' + random.nextInt(10)));
            }

            // 将纯数字的UUID字符串转为long
            return Long.parseLong(numericUUIDStr.toString());
        }

}
