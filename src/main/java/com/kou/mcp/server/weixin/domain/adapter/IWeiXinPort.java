package com.kou.mcp.server.weixin.domain.adapter;

import com.kou.mcp.server.weixin.domain.model.WeiXinNoticeFunctionRequest;
import com.kou.mcp.server.weixin.domain.model.WeiXinNoticeFunctionResponse;

import java.io.IOException;

/**
 * @author KouJY
 * @description
 * @create 2025-07-04 9:01
 */
public interface IWeiXinPort {

    WeiXinNoticeFunctionResponse weixinNotice(WeiXinNoticeFunctionRequest request) throws IOException;
}
