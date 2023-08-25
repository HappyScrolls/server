package com.HappyScrolls.service;

import com.HappyScrolls.repository.ArticleTagRepository;
import com.HappyScrolls.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;


}
