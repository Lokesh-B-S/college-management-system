package com.ras.cms.service.departmentProgramFetch;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentAndProgramFetch;
import com.ras.cms.domain.Program;
import com.ras.cms.service.department.DepartmentService;
import com.ras.cms.service.program.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class DepartmentProgramFetchServiceImpl implements DepartmentProgramFetchService{


    @Autowired
   DepartmentService departmentService;

    @Autowired
    ProgramService programService;
    public DepartmentAndProgramFetch processRequest(HttpServletRequest request) {

        String userName = request.getUserPrincipal().getName();
        DepartmentAndProgramFetch departmentAndProgramFetch = getDepartmentAndProgramFromUserName(userName);

        return departmentAndProgramFetch;
    }

    public DepartmentAndProgramFetch getDepartmentAndProgramFromUserName(String userName) {
        String department = "";
        String program = "";
        if ("h".equals(userName)) {
            department = "Computer Science";
            program = "BE - computer science";
        } else if ("hodise".equals(userName)) {
            department = "Information Science";
            program = "BE - information science";
        } else if ("hodmech".equals(userName)) {
            department = "Mechanical";
            program = "BE - Mechanical";
        } else if ("hodiem".equals(userName)) {
            department = "Industrial Engineering and Management";
            program = "BE - Industrial Engineering and Management";
        }

        Department dep = departmentService.findByDepartmentName(department);
        Program prog = programService.findByProgramName(program);


        DepartmentAndProgramFetch departmentAndProgramFetch = new DepartmentAndProgramFetch();
        departmentAndProgramFetch.setDepartment(dep);
        departmentAndProgramFetch.setProgram(prog);

        return departmentAndProgramFetch;
    }
}

