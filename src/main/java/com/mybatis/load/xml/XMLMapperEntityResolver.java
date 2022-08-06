package com.mybatis.load.xml;

import com.mybatis.io.Resource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * XmlMaper实体解析器
 * 使用sax
 */
public class XMLMapperEntityResolver implements EntityResolver {

    private static final String MYBATIS_CONDIG_SYSTEM = "mybatis-3-config.dtd";
    private static final String MYBATIS_CONFIG_DTD = "com/mybatis/load/xml/mybatis-3-config.dtd";

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
        if(lowerCaseSystemId.contains(MYBATIS_CONDIG_SYSTEM)){
            return getInputSource(MYBATIS_CONFIG_DTD, publicId, systemId);
        }

        return null;
    }

    private InputSource getInputSource(String path, String publicId, String systemId) {
        InputSource source = null;
        if(path != null){
            InputStream in = Resource.getResourceAsStream(path);
            source = new InputSource(in);
            source.setPublicId(publicId);
            source.setSystemId(systemId);
        }
        return source;
    }
}
