package com.ras.cms.service.section;

import com.ras.cms.domain.Section;

import java.util.List;

public interface SectionService {

    List<Section> findAll();

    Section saveSection(Section section);
}
