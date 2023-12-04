package com.ras.cms.service.departmentProgramFetch;

import com.ras.cms.domain.DepartmentAndProgramFetch;

import javax.servlet.http.HttpServletRequest;

public interface DepartmentProgramFetchService {

    DepartmentAndProgramFetch processRequest(HttpServletRequest request);
}
