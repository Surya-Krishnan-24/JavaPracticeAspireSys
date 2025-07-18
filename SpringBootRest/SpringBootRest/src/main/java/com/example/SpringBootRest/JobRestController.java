package com.example.SpringBootRest;

import com.example.SpringBootRest.model.JobPost;
import com.example.SpringBootRest.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class JobRestController {

    @Autowired
    private JobService jobService;

    @GetMapping(path = "jobPosts", produces = {"application/json"})
    public List<JobPost> getAllJob(){
        return jobService.getAllJobs();
    }

    @GetMapping("jobPost/{postID}")
    public JobPost getJob(@PathVariable("postID") int postID){
        return jobService.getJob(postID);
    }

    @GetMapping("jobPosts/keyword/{keyword}")
    public List<JobPost> searchByKeyword(@PathVariable("keyword") String keyword){
        return jobService.search(keyword);
    }

    @PostMapping(path = "jobPost")
    public String addJob(@RequestBody JobPost jobPost){
        return jobService.addJob(jobPost);

    }

    @PutMapping("jobPost")
    public String updateJob(@RequestBody JobPost jobPost){
        return jobService.updateJob(jobPost);
    }

    @DeleteMapping("jobPost/{id}")
    public String deleteJob(@PathVariable int id ){
        return jobService.deleteJob(id);
    }
}
