package com.maziV5.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maziV5.eduservice.entity.EduChapter;
import com.maziV5.eduservice.entity.EduVideo;
import com.maziV5.eduservice.entity.chapter.ChapterVo;
import com.maziV5.eduservice.entity.chapter.VideoVo;
import com.maziV5.eduservice.mapper.EduChapterMapper;
import com.maziV5.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maziV5.eduservice.service.EduVideoService;
import com.maziV5.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程章节 服务实现类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService videoService;

    //课程大纲查询，根据course_id
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查询所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = this.list(wrapperChapter);

        //查询所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //封装
        ArrayList<ChapterVo> finalList = new ArrayList<>();

        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            ArrayList<VideoVo> list = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                VideoVo videoVo = new VideoVo();
                //判断小节的chapter_id和章节的id一致
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    BeanUtils.copyProperties(eduVideo,videoVo);

                    list.add(videoVo);
                }
            }

            chapterVo.setChildren(list);
            finalList.add(chapterVo);
        }

        return finalList;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001,"无法删除，该章节中有小节");
        } else {
            boolean flag = this.removeById(chapterId);
            return flag;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        this.remove(queryWrapper);
    }
}
