package com.ying.mybatis.io;

import java.io.InputStream;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc
 */
public class Resources {

    public static InputStream getResourceAsStream(String location) {
        return Resources.class.getClassLoader().getResourceAsStream(location);
    }
}
