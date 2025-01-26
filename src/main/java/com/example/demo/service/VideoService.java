package com.example.demo.service;

import com.example.demo.model.Videos;
import com.example.demo.repository.VideoRepocitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    VideoRepocitory videoRepocitory;


    public Videos saveVideos(Videos videos){
        return  videoRepocitory.save(videos);
    }

    public String updateVideos(Videos videos,String id) {

        Optional<Videos> videos1=videoRepocitory.findById(id);
        if(videos1.isPresent()){
            videos1.get().setDirector(videos.getDirector());
            videos1.get().setGenre(videos.getGenre());
            videos1.get().setTitle(videos.getTitle());
            videos1.get().setIsavailable(videos.getIsavailable());
            videoRepocitory.save(videos1.get());
            return "Success";
        }else {
            return "No Video Found with id"+id;
        }
    }

    public String deleteVideos(String id) {
        Optional<Videos> videos1=videoRepocitory.findById(id);
        if(videos1.isPresent()){
            videoRepocitory.delete(videos1.get());
            return "Success";
        }else {
        throw new RuntimeException("No Video Found with id"+id);
    }
    }

    public List<Videos> getAll() {
        List<Videos> videosList=new ArrayList<>();
        videosList.addAll(videoRepocitory.findAll());
        return videosList;
    }
}
