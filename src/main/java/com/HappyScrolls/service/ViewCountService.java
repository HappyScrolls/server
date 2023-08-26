package com.HappyScrolls.service;

import com.HappyScrolls.repository.ViewCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewCountService {

    @Autowired
    private ViewCountRepository viewCountRepository;


}
