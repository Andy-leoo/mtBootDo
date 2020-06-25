package com.bootdo.train.service;

import com.bootdo.train.pojo.UserDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ExportService {
    void exportUserExcel(HttpServletRequest request, HttpServletResponse resp, List<UserDO> userList, String fileName, String[] title) throws UnsupportedEncodingException;
}
