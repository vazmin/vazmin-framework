package com.github.vazmin.framework.core.mybatis.handler;

import com.github.vazmin.framework.core.mybatis.crypto.StringEncryptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段加解密处理, 无法解密返回原值
 * 需要在xml中指定TypeHandler
 * 返回值ResultMap:
 * <result column="" jdbcType="VARCHAR" property="" typeHandler="secretStringTypeHandler"/>
 * 参数:
 * <select> xxx where email = #{email, typeHandler=secretStringTypeHandler}</select>
 */
public class SecretStringTypeHandler implements TypeHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(SecretStringTypeHandler.class);

    private StringEncryptor stringEncryptor;

    public SecretStringTypeHandler() {
        log.debug("new SecretStringTypeHandler()");
    }

    public SecretStringTypeHandler(StringEncryptor stringEncryptor) {
        log.debug("new SecretStringTypeHandler(StringEncryptor stringEncryptor)");
        this.stringEncryptor = stringEncryptor;
    }

    public StringEncryptor getStringEncryptor() {
        return stringEncryptor;
    }

    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, getEncryptResult(parameter));
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return getDecryptResult(rs.getString(columnName));
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return getDecryptResult(rs.getString(columnIndex));
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }

    /**
     * 加密字段
     * @param result 原始值
     * @return 加密值
     */
    private String getEncryptResult(String result) {
        if (StringUtils.isBlank(result)) {
            return result;
        }
        try {
            result = stringEncryptor.encrypt(result);
        } catch (Exception e) {
            log.error("set param fail: {}, {}", result, e.getMessage());
        }
        return result;
    }

    /**
     * 解密字段
     * @param result 加密值
     * @return 原始值
     */
    private String getDecryptResult(String result) {
        if (StringUtils.isBlank(result)) {
            return result;
        }
        try {
            result = stringEncryptor.decrypt(result);
        } catch (Exception e) {
            log.error("get result fail: {}, {}", result, e.getMessage());
        }
        return result;
    }
}
