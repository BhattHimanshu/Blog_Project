package com.blog.demoblog.service;

import com.blog.demoblog.Entity.TagEntity;
import com.blog.demoblog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;
public void saveTag(TagEntity tagEntity){
    tagRepository.save(tagEntity);
}

public List<TagEntity> findTagIds(String StringDataFromHtmlViaController){
String[] strA = StringDataFromHtmlViaController.trim().split(",");
for(String data : strA){
    boolean isExist = tagRepository.existsByName(data);//fetch all tags from database
    if(!isExist){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(data);
        tagEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        tagEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        tagRepository.save(tagEntity); // We are also seeing if it dosn't exist on main tag database add it
    }
}
    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(strA));
return  tagRepository.findAllByName(arrayList);// Returning List of Tags only in STring from controllerr
}
    public List<TagEntity> findAll(){
        return tagRepository.findAll();
    }
}
