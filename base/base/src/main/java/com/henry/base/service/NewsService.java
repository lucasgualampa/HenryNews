package com.henry.base.service;

import com.henry.base.model.News;
import com.henry.base.model.PaginationResponse;
import com.henry.base.model.Writer;
import com.henry.base.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    private WriterService writerService;

    public NewsService(NewsRepository newsRepository, WriterService writerService) {
        this.newsRepository = newsRepository;
        this.writerService = writerService;
    }

    public News getNewsById(Integer id){
        return newsRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public News addNew(News objNew){
        return newsRepository.save(objNew);
    }

    public List<News> getAll(){
        return newsRepository.findAll();
    }

    public PaginationResponse<News> getAll(Integer page, Integer size){
        if (!Objects.isNull(page) && !Objects.isNull(size)){
            Pageable pageable = PageRequest.of(page, size);
            Page<News> newsPage = newsRepository.findAll(pageable);
            return new PaginationResponse<>(newsPage.getContent(), newsPage.getTotalPages(), newsPage.getTotalElements());
        }
        List<News> newsList = newsRepository.findAll();
        return new PaginationResponse<>(newsList, 1, (long) newsList.size());
    }

    public void addWriter(Integer id, Integer writerID){
        News news = getNewsById(id);
        Writer writer = writerService.getWriter(writerID);
        news.setOwner(writer);
        newsRepository.save(news);
    }

    public void deleteWriterById(Integer id){
        newsRepository.deleteById(id);
    }
}
