package com.lmy.service;

import com.lmy.pojo.EmpLog;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface EmpLogService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLog(EmpLog empLog);

}
