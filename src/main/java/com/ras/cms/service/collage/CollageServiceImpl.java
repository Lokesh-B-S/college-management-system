package com.ras.cms.service.collage;

import com.ras.cms.dao.CollageRepository;
import com.ras.cms.domain.Collage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollageServiceImpl implements CollageService{

    @Autowired
    CollageRepository collageRepository;

    @Override
    public List<Collage> findAll() {
        return collageRepository.findAll();
    }

    @Override
    public Collage findOne(Long id) {
        return collageRepository.findById(id).get();
    }

    @Override
    public Collage saveCollage(Collage collage) {
        return collageRepository.save(collage);
    }

    @Override
    public void deleteCollage(Long id) {
        collageRepository.deleteById(id);
    }
}