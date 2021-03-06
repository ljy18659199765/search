package com.spider.service.timer;

import com.spider.config.ActionNames;
import com.spider.localservice.HttpClientLocalService;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.TIMER_STOP_HTTP_CLIENT,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "停止httpclient服务")
public class TimerStopHttpClientServiceImpl implements Service {

    //
    @InjectLocalService()
    private HttpClientLocalService httpClientLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        this.httpClientLocalService.unready();
        messageContext.success();
    }
}
