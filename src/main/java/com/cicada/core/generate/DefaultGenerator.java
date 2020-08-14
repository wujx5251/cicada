package com.cicada.core.generate;


import com.cicada.Config;
import com.cicada.Constants;
import com.dotin.common.utils.DateUtils;
import com.dotin.common.utils.NumberUtils;
import com.dotin.common.utils.StringUtils;
import com.dotin.redis.core.AtomicCommands;
import com.dotin.redis.factory.RedisFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 生成器
 *
 * @author: WUJXIAO
 * @create: 2018-12-26 09:10
 * @version: 1.0
 */
@Component
public class DefaultGenerator implements Generator {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGenerator.class);

    private final static AtomicCommands commands = RedisFactory.getClusterAtomicCommands(Constants.GROUP_NAME);

    private static final char[] ALL = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
           /* '!', '@', '#', '$', '%',  '&'*/
    };

    @Override
    public String generateNo() {

        Date date = DateUtils.now();

        String dateStr = DateUtils.format(date, "yyyyMMddHHmm");

        String part1 = StringUtils.leftPad(Config.getFlag(), 2, '0');
        String part2 = Long.toOctalString(Long.parseLong(DateUtils.format(date, "yyyyMMddHHmm")));
        String part3;

        String key = "record_no_" + dateStr;

        Long value = commands.increment(Constants.NAMESPACE_DAO_MIN, key, 1L);

        if (NumberUtils.isEquals(value, 1))
            commands.expire(Constants.NAMESPACE_DAO_MIN, key, 5L, TimeUnit.MINUTES);

        part3 = StringUtils.leftPad(String.valueOf(value), 3, '0');

        String newRecordNo = part1 + part2 + part3;

        return newRecordNo;
    }

    @Override
    public String generateSalt(int size) {
        return RandomStringUtils.random(size, ALL);
    }
}
