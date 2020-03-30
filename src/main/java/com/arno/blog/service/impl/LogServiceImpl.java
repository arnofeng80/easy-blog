package com.arno.blog.service.impl;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arno.blog.excel.entity.ExportParams;
import com.arno.blog.excel.handler.ExcelExportHandler;
import com.arno.blog.mapper.LogMapper;
import com.arno.blog.pojo.Log;
import com.arno.blog.service.LogService;
import com.arno.blog.utils.Page;

/**
 * <p>
 * 接口访问日志表服务实现类
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 保存
     * @param logger
     */
    @Override
    public void save(Log logger) {
        logMapper.save(logger);
    }

    @Override
    public Page<Log> getByPage(Page<Log> page) {
        // 查询数据
        List<Log> logList = logMapper.getByPage(page);
        page.setList(logList);
        // 查询总数
        int totalCount = logMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public void deleteById(Integer id) {
        logMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        logMapper.deleteByIds(ids);
    }

    @Override
    public Workbook export() {
        List<Log> logList = logMapper.getAll();
        return new ExcelExportHandler().createSheet(new ExportParams("最新日志", "sheet1"), Log.class, logList);
    }
}
