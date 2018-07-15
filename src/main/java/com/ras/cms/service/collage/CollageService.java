package com.ras.cms.service.collage;

import com.ras.cms.domain.Collage;

import java.util.List;

public interface CollageService {
    List<Collage> findAll();
    Collage findOne(Long id);
    Collage saveCollage(Collage collage);
    void deleteCollage(Long id);
}
