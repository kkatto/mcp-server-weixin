package com.kou.mcp.server.weixin.domain.service;

import com.kou.mcp.server.weixin.domain.adapter.IWeiXinPort;
import com.kou.mcp.server.weixin.domain.model.WeiXinNoticeFunctionRequest;
import com.kou.mcp.server.weixin.domain.model.WeiXinNoticeFunctionResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author KouJY
 * @description 微信通知服务接口
 * @create 2025-07-05 10:10
 */
@Slf4j
@Service
public class WeiXinNoticeService {

    @Resource
    private IWeiXinPort weiXinPort;

    @Tool(description = "微信通知服务接口")
    public WeiXinNoticeFunctionResponse weixinNotice(WeiXinNoticeFunctionRequest request) throws IOException {
        log.info("微信消息通知，平台:{} 主题:{} 描述:{}", request.getPlatform(), request.getSubject(), request.getDescription());
        return weiXinPort.weixinNotice(request);
    }
}
