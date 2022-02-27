package com.activeclub.fileserverminio.core.utils.impl;

import com.activeclub.fileserverminio.core.utils.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SessionServiceImpl implements SessionService {

    /**
     * 获取当前session中的账户名
     *
     * @return
     */
    @Override
    public String getPreAccountName() {
        return "admin";
    }

    /**
     * todo 后续优化
     *
     * @param accountName
     * @return
     */
    @Override
    public String getPreAccountName(String accountName) {
        return (StringUtils.hasLength(accountName) ? accountName : getPreAccountName());
    }
}
