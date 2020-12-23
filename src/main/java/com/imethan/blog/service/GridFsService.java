package com.imethan.blog.service;

import com.imethan.blog.dto.ImageDto;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Name GridFsService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-23 16:19
 */
@Service
@Log4j2
public class GridFsService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public ImageDto saveImage(MultipartFile uploadFile) {

        ImageDto imageDto = new ImageDto();

        //获取文件名
        String fileName = uploadFile.getOriginalFilename();
        //文件后缀
        String extension = FilenameUtils.getExtension(fileName);

        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
            DBObject metaData = new BasicDBObject();
            metaData.put("ext", extension);
            metaData.put("name", fileName);
            ObjectId objectId = gridFsTemplate.store(inputStream, fileName, "image", metaData);

            imageDto.setSuccess(1);
            imageDto.setMessage("上传成功");
            imageDto.setUrl("/api/article/image/" + objectId);

        } catch (IOException e) {
            imageDto.setSuccess(0);
            imageDto.setMessage("上传失败");
            e.printStackTrace();
            log.error("gridfs save file error msg=" + e.getMessage());
        }

        return imageDto;
    }


    public GridFsResource getImage(String id) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        if(gridFSFile == null){
            return null;
        }
        return gridFsTemplate.getResource(gridFSFile);

    }
}
