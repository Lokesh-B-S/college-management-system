package com.ras.cms.service.section;

import com.ras.cms.domain.Section;

import java.util.List;

public interface SectionService {


    Section findOne(Long secId);
    List<Section> findAll();

    Section saveSection(Section section);
}
