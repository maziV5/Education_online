package com.maziV5.eduservice.service;

import com.maziV5.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maziV5.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程章节 服务类
 * </p>
 *
 * @author maziV5
 * @since 2023-03-30
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
