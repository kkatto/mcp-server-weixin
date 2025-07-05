package com.kou.mcp.server.weixin.infrastructure.adapter;

import com.google.common.cache.Cache;
import com.kou.mcp.server.weixin.domain.adapter.IWeiXinPort;
import com.kou.mcp.server.weixin.domain.model.WeiXinNoticeFunctionRequest;
import com.kou.mcp.server.weixin.domain.model.WeiXinNoticeFunctionResponse;
import com.kou.mcp.server.weixin.infrastructure.gateway.IWeixinApiService;
import com.kou.mcp.server.weixin.infrastructure.gateway.dto.WeixinTemplateMessageDTO;
import com.kou.mcp.server.weixin.infrastructure.gateway.dto.WeixinTokenResponseDTO;
import com.kou.mcp.server.weixin.types.properties.WeiXinApiProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KouJY
 * @description
 * @create 2025-07-04 9:01
 */
@Slf4j
@Component
public class WeiXinPort implements IWeiXinPort {

    @Resource
    private WeiXinApiProperties properties;

    @Resource
    private IWeixinApiService weixinApiService;

    @Resource
    private Cache<String, String> weixinAccessToken;

    @Override
    public WeiXinNoticeFunctionResponse weixinNotice(WeiXinNoticeFunctionRequest request) throws IOException {
        // 1. 获取 accessToken
        String accessToken = weixinAccessToken.getIfPresent(properties.getAppid());
        if (null == accessToken) {
            Call<WeixinTokenResponseDTO> call = weixinApiService.getToken("client_credential", properties.getAppid(), properties.getAppsecret());
            WeixinTokenResponseDTO weixinTokenResponseDTO = call.execute().body();
            assert weixinTokenResponseDTO != null;
            accessToken = weixinTokenResponseDTO.getAccess_token();
            weixinAccessToken.put(properties.getAppid(), accessToken);
        }

        // 2. 发送模板消息
        Map<String, Map<String, String>> data = new HashMap<>();
        WeixinTemplateMessageDTO.put(data, WeixinTemplateMessageDTO.TemplateKey.platform, request.getPlatform());
        WeixinTemplateMessageDTO.put(data, WeixinTemplateMessageDTO.TemplateKey.subject, request.getSubject());
        WeixinTemplateMessageDTO.put(data, WeixinTemplateMessageDTO.TemplateKey.description, request.getDescription());

        WeixinTemplateMessageDTO weixinTemplateMessageDTO = new WeixinTemplateMessageDTO(properties.getTouser(), properties.getTemplate_id());
        weixinTemplateMessageDTO.setUrl(request.getJumpUrl());
        weixinTemplateMessageDTO.setData(data);

        Call<Void> call = weixinApiService.sendMessage(accessToken, weixinTemplateMessageDTO);
        call.execute();

        WeiXinNoticeFunctionResponse response = new WeiXinNoticeFunctionResponse();
        response.setSuccess(true);
        return response;
    }
}
