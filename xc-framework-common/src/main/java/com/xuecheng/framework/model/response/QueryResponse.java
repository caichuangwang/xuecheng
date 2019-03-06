package com.xuecheng.framework.model.response;

/**
 * @author 蔡闯王
 * @date 2019/2/5 17:29
 */
public class QueryResponse<T> extends ResponseResult {
    QueryResult queryResult;

    public QueryResponse(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }
}
