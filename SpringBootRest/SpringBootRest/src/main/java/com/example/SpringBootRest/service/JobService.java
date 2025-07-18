package com.example.SpringBootRest.service;

import com.example.SpringBootRest.model.JobPost;
import com.example.SpringBootRest.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {


    @Autowired
    public JobRepo repo;

    public String updateJob(JobPost jobPost) {
        repo.save(jobPost);
        return "updated Successfully";
    }


    // method to add a jobPost
    public String addJob(JobPost jobPost) {
        repo.save(jobPost);
        return "saved successfully";

    }


    //method to return all JobPosts
    public List<JobPost> getAllJobs() {
        return repo.findAll();


    }


    public JobPost getJob(int postID) {
        return repo.findById(postID).orElse(new JobPost());
    }

    public String deleteJob(int id) {
        repo.deleteById(id);
        return "Deleted";
    }

    public List<JobPost> search(String keyword) {
        return repo.findByPostProfileContainingOrPostDescContaining(keyword,keyword);
    }
}
