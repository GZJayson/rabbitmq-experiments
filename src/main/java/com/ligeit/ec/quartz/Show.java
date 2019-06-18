package com.ligeit.ec.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *原 quartz 示例
 *
 * @author ywx
 * @date 2019/5/28
 */
@Service("show")
public class Show implements Job {


    private static Logger _log = LoggerFactory.getLogger(Show.class);

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

        _log.info("\n\n-------------------------------\n " +
                "It is running and the time is : " + new Date()+
                "\n-------------------------------\n");
    }
}