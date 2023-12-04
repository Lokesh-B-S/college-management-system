package com.ras.cms.service.departmentsemsec;

import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentSemSec;

import java.util.List;

public interface DepartmentSemSecService {

    DepartmentSemSec findOne(Long id);

    List<DepartmentSemSec> getAllEntries();

    DepartmentSemSec saveEntry(DepartmentSemSec bss);

    boolean existsEntry(DepartmentSemSec departmentSemSec);

    Long findId(DepartmentSemSec departmentSemSec);

    DepartmentSemSec findRow(DepartmentSemSec departmentSemSec);

    Department findDepartment(Long departmentSemSecId);

}
