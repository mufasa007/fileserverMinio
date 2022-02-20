package com.activeclub.fileserverminio.core.utils.impl;

import com.activeclub.fileserverminio.core.utils.SessionService;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    /**
     * todo 后续优化
     * @return
     */
    @Override
    public String getPreUser() {
        return "admin";
    }
}
