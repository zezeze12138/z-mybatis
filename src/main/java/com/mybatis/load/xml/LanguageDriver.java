package com.mybatis.load.xml;

import com.mybatis.load.document.XNode;
import com.mybatis.mapping.SqlSource;
import com.mybatis.session.Configuration;

/**
 * 语言驱动
 * 这里主要将节点转换为sql
 */
public interface LanguageDriver {

    /**
     * 创建sql资源
     * @param configuration
     * @param script
     * @param paramterType
     * @return
     */
    SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> paramterType);

}
