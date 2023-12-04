package com.ras.cms.service.termService;

import com.ras.cms.domain.Semester;
import com.ras.cms.domain.Term;

import java.util.List;

public interface TermService {

    List<Term> findAll();

    Term saveTerm(Term term);
}
