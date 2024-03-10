package com.ras.cms.service.termService;

import com.ras.cms.repository.TermRepository;
import com.ras.cms.domain.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImpl implements TermService{

    @Autowired
    TermRepository termRepository;


    public Term findOne(Long id){return termRepository.findById(id).get();}
    public List<Term> findAll(){
        return termRepository.findAll();
    }

    public Term saveTerm(Term term){
        return termRepository.save(term);
    }
}
