package com.ras.cms.service.section;

import com.ras.cms.repository.SectionRepository;
import com.ras.cms.domain.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService{

    @Autowired
    private SectionRepository sectionRepository;

    public List<Section> findAll(){return sectionRepository.findAll();}

    public Section saveSection(Section section){return sectionRepository.save(section);}


}
