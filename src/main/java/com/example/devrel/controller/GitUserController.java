package com.example.devrel.controller;

import com.example.devrel.model.Follover;
import com.example.devrel.model.GitRepo;
import com.example.devrel.model.GitTable;
import com.example.devrel.service.GitUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class GitUserController {
    private final GitUserService gitUserService;

    public GitUserController(GitUserService gitUserService) {
        this.gitUserService = gitUserService;
    }

//    @GetMapping("follovers/{gitName}")
//    public List<Follover>getFollovers(@PathVariable String gitName){
//    return gitUserService.getFolloverList(gitName);
//}

    @GetMapping("contributors/{gitName}")
    public Set<String> getContributors(@PathVariable String gitName){
        return gitUserService.getUnicContrubuturs(gitName);
    }

    @GetMapping("doublecontributors/{gitName}")
    public Set<String> getDoubleContributors(@PathVariable String gitName){
        return gitUserService.getDoubleUnicContrubuturs(gitName);
    }

    @GetMapping("table/{gitName}/{countFollowers}")
    public GitTable getGitTable(@PathVariable String gitName, @PathVariable Integer countFollowers ){
        return gitUserService.getTable(gitName, countFollowers);
    }

}
