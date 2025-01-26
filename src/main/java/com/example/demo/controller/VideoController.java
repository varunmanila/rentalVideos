package com.example.demo.controller;

import com.example.demo.model.Videos;
import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    VideoService videoService;
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Videos AddVideos(@RequestBody Videos videos) {

        return  videoService.saveVideos(videos);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String UpdateVideos(@RequestBody Videos videos,@RequestParam String id) {

        return  videoService.updateVideos(videos,id);
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String DeleteVideos(@RequestParam String id) {

        return  videoService.deleteVideos(id);
    }

    @GetMapping("/list")
    public List<Videos> VideosList() {
        return  videoService.getAll();
    }
}
