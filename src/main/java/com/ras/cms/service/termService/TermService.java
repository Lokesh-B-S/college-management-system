package com.ras.cms.service.termService;

import com.ras.cms.domain.Semester;
import com.ras.cms.domain.Term;

import java.util.List;

public interface TermService {


    Term findOne(Long id);

    List<Term> findAll();

    Term saveTerm(Term term);
}
