package com.mybatis.load.document;

import com.mybatis.load.xml.DynamicContext;

public interface SqlNode {

    boolean apply(DynamicContext context);

}
