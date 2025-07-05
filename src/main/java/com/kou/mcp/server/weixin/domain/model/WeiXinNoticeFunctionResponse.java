package com.kou.mcp.server.weixin.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * @author KouJY
 * @description
 * @create 2025-07-04 9:00
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeiXinNoticeFunctionResponse {

    @JsonProperty(required = true, value = "success")
    @JsonPropertyDescription("success")
    private boolean success;
}
