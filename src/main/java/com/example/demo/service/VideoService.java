package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Videos;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VideoRepocitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    VideoRepocitory videoRepocitory;
    @Autowired
    UserRepository userRepository;


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

    public ResponseEntity<?> rentVideos(String videoId, String customerId) {
        Optional<User> user=userRepository.findById(customerId);
        if(user.isPresent()){
            int totalVIdeos= user.get().getVideos() != null ? user.get().getVideos().size() : 0;
            if(totalVIdeos==2){
                throw new RuntimeException("User Already booked maximum possible videos");
            }
            Optional<Videos> videos1=videoRepocitory.findById(videoId);
            ArrayList<Videos> videos=user.get().getVideos()
            if(videos1.isPresent()){

                videos.add(videos1.get());
            }
            user.get().setVideos(videos);
            userRepository.save(user.get());
            return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
            }else {
                throw new RuntimeException("No Video Found with id"+videoId);
        }
    }

    public ResponseEntity<?> returnVideos(String videoId, String customerId) {
        Optional<User> user=userRepository.findById(customerId);
        if(user.isPresent()){
            int totalVIdeos= user.get().getVideos() != null ? user.get().getVideos().size() : 0;
            if(totalVIdeos==0){
                throw new RuntimeException("User Already booked maximum possible videos");
            }
            Optional<Videos> videos1=videoRepocitory.findById(videoId);
            List<Videos> videos=user.get().getVideos();
            if(videos1.isPresent()){
                videos.removeIf(video -> video.getId().equals(videoId));
                user.get().setVideos(videos);
                userRepository.save(user.get());
            }
            return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
        }else {
            throw new RuntimeException("No Video Found with id"+videoId);
        }
    }
}
