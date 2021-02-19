package com.demo.system.controller;

import com.demo.system.model.suo.AppProjectSuo;
import com.demo.system.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
@RestController
@Api(tags = "项目管理")
@Slf4j
@RequestMapping("/api")
public class AppProjectController {

    @PostMapping("/app")
    @ApiOperation(value = "项目申请")
    public ResponseResult apply(@ApiParam(value = "项目申请参数", required = true) @RequestBody AppProjectSuo appProjectSuo) {

        return ResponseResult.success();
    }
}
