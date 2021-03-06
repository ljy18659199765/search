package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.localservice.HttpClientLocalService;
import com.spider.localservice.SourceLocalService;
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
        actionName = ActionNames.UPDATE_ALL_SOURCE_SESSION,
parameterTypeEnum = ParameterTypeEnum.NO_PARAMETER,
validateSession = false,
response = true,
description = "更新第三方渠道登录信息")
public class UpdateAllSourceSessionServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //
    @InjectLocalService()
    private HttpClientLocalService httpClientLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        this.httpClientLocalService.unready();
        this.sourceLocalService.updateAllSourceSession();
        this.httpClientLocalService.init();
        this.httpClientLocalService.ready();
        messageContext.success();
    }
}
