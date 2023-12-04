package com.ras.cms.service.departmentsemsec;

import com.ras.cms.repository.DepartmentSemSecRepository;
import com.ras.cms.domain.Department;
import com.ras.cms.domain.DepartmentSemSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentSemSecImpl implements DepartmentSemSecService {

    @Autowired
    DepartmentSemSecRepository departmentSemSecRepository;

    @Override
    public DepartmentSemSec findOne(Long id) {
        return departmentSemSecRepository.findById(id).get();
    }

    @Override
    public List<DepartmentSemSec> getAllEntries() {
        return departmentSemSecRepository.findAll();
    }
    @Override
    public DepartmentSemSec saveEntry(DepartmentSemSec bss){
        return departmentSemSecRepository.save(bss);
    }

    @Override
    public boolean existsEntry(DepartmentSemSec departmentSemSec){
        return departmentSemSecRepository.existsByDepartmentAndSemesterAndSection(departmentSemSec.getDepartment(),departmentSemSec.getSemester(),departmentSemSec.getSection());
}

@Override
    public Long findId(DepartmentSemSec departmentSemSec){
        return departmentSemSecRepository.findIdByDepartmentAndSemesterAndSection(departmentSemSec.getDepartment(),departmentSemSec.getSemester(),departmentSemSec.getSection());
}

@Override
    public DepartmentSemSec findRow(DepartmentSemSec departmentSemSec){
        return departmentSemSecRepository.findByDepartmentAndSemesterAndSection(departmentSemSec.getDepartment(),departmentSemSec.getSemester(),departmentSemSec.getSection());
}

    @Override
    public Department findDepartment(Long departmentSemSecId){return departmentSemSecRepository.findDepartmentByDepartmentSemSecId(departmentSemSecId);}

}
