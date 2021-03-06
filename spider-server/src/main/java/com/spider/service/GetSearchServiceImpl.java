package com.spider.service;

import com.spider.config.ActionNames;
import com.spider.config.SourceEnum;
import com.spider.localservice.SourceLocalService;
import com.spider.localservice.SpiderDataLocalService;
import com.spider.parameter.SourceParameter;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.ParameterTypeEnum;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.utils.JsonUtils;
import com.wolf.framework.worker.context.MessageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.GET_SEARCH,
parameterTypeEnum = ParameterTypeEnum.PARAMETER,
importantParameter = {"tag", "location", "source"},
returnParameter = {"sourceIdArr"},
parametersConfigs = {SourceParameter.class},
validateSession = false,
response = true,
description = "抓取第三方渠道搜索结果服务")
public class GetSearchServiceImpl implements Service {

    @InjectLocalService()
    private SourceLocalService sourceLocalService;
    //
    @InjectLocalService()
    private SpiderDataLocalService spiderDataLocalService;
    //

    @Override
    public void execute(MessageContext messageContext) {
        String tag = messageContext.getParameter("tag");
        String location = messageContext.getParameter("location");
        String source = messageContext.getParameter("source");
        SourceEnum sourceEnum = SourceEnum.valueOf(source);
        String text;
        String textId;
        Map<String, String> insertMap;
        List<Map<String, String>> insertMapList = new ArrayList<Map<String, String>>(50);
        Set<String> sourceIdSet = new HashSet<String>(100, 1);
        List<String> sourceIdList;
        StringBuilder textIdPrefixBuilder = new StringBuilder(64);
        textIdPrefixBuilder.append(source).append('_').append(location).append('_').append(tag).append('_');
        final String textIdprefix = textIdPrefixBuilder.toString();
        for (int pageIndex = 1; pageIndex <= 50; pageIndex++) {
            text = this.sourceLocalService.getSearchText(sourceEnum, location, tag, pageIndex);
            if (text.length() == 0) {
                break;
            } else {
                //解析
                sourceIdList = this.sourceLocalService.parseSearchText(sourceEnum, text);
                sourceIdSet.addAll(sourceIdList);
                //
                textId = textIdprefix.concat(Integer.toString(pageIndex));
                insertMap = new HashMap<String, String>(2, 1);
                insertMap.put("id", textId);
                insertMap.put("text", text);
                insertMapList.add(insertMap);
            }
        }
        if (sourceIdSet.isEmpty() == false) {
            this.spiderDataLocalService.batchInsertSearchData(insertMapList);
            String sourceIdJson = JsonUtils.setToJSON(sourceIdSet);
            Map<String, String> resultMap = new HashMap<String, String>(2, 1);
            resultMap.put("sourceIdArr", sourceIdJson);
            messageContext.setMapData(resultMap);
            messageContext.success();
        }
    }
}
