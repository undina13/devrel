package com.example.devrel.service;

import com.example.devrel.model.Follover;
import com.example.devrel.model.GitRepo;
import com.example.devrel.model.GitTable;
import com.example.devrel.model.GitUser;
import com.example.devrel.repository.GitUserRepository;
import org.springframework.boot.web.client.RestTemplateRequestCustomizer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Table;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GitUserService {
    private final GitUserRepository gitUserRepository;

    public GitUserService(GitUserRepository gitUserRepository) {
        this.gitUserRepository = gitUserRepository;
    }
    public void getFollovers(String gitName, int countFollowers) {
        RestTemplate restTemplate = new RestTemplate();
        int count = countFollowers/100 + 1;
        for (int i = 1; i <=count ; i++) {
            String baseUrl = "https://api.github.com/users/" + gitName + "/followers?per_page=100&page="+ i;
            ResponseEntity<List<Follover>> responseEntity =
                    restTemplate.exchange(
                            baseUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Follover>>() {
                            }
                    );
            addUsersInDatabase(responseEntity.getBody());
        }



    }

    public void addUsersInDatabase(List<Follover> follovers){
        RestTemplate restTemplate = new RestTemplate();
        for(Follover follover:follovers){
            GitUser gitUser = restTemplate.getForObject(
                    URI.create(follover.getUrl()), GitUser.class);
            gitUserRepository.save(gitUser);
        }
    }

    public  List<List<String>>getRepoList(String gitName){
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://api.github.com/users/" + gitName + "/repos";
        ResponseEntity<List<GitRepo>> responseEntity =
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GitRepo>>() {
                        }
                );

       return getUsersContributors(responseEntity.getBody());

    }

    public List<List<String>>getUsersContributors(List<GitRepo> repoList){
        RestTemplate restTemplate = new RestTemplate();
        List<List<String>> list = new ArrayList<>();
        for(GitRepo gitRepo:repoList){
            ResponseEntity<List<Follover>> responseEntity =
                    restTemplate.exchange(
                            URI.create(gitRepo.getContributors_url()),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Follover>>() {
                            }
                    );
         List<Follover> follovers =   responseEntity.getBody();
         List<String>list1 = new ArrayList<>();
         for (Follover follover:follovers){
             list1.add(follover.getUrl());
         }
         list.add(list1);
        }
      return list;
    }

    public Set<String>getUnicContrubuturs(String gitName){
        List<List<String>> list = getRepoList(gitName);
        Set<String>set = new HashSet<>();
        for (List<String> stringList:list){
            for (String s:stringList){
                set.add(s);

            }
        }
        return set;
    }
    public Set<String>getDoubleUnicContrubuturs(String gitName){
        List<List<String>> list = getRepoList(gitName);
        Set<String>set = new HashSet<>();
        for (int i = 1; i < list.size()-1; i++) {
           List<String> newList = list.get(i-1).stream().filter(list.get(i)::contains).collect(Collectors.toList());
           for (String s: newList){
               set.add(s);
           }
        }

        return set;
    }

    public GitTable getTable(String gitName, int countFollowers) {
        GitTable gitTable = new GitTable();
        gitTable.setName(gitName);

        RestTemplate restTemplate = new RestTemplate();
        GitUser gitUser = restTemplate.getForObject(
                URI.create("https://api.github.com/users/"+ gitName), GitUser.class);


        assert gitUser != null;
       int followers = gitUser.getFollowers();
     //  getFollovers(gitName, followers);
        gitTable.setCountFollowers(followers);


        gitTable.setCountRepo(gitUser.getPublic_repos());
        Set<String>unicContributors = getUnicContrubuturs(gitName);
        gitTable.setCountContributors(unicContributors.size());
        Set<String>unicDoubleContributors = getDoubleUnicContrubuturs(gitName);
        gitTable.setCountDoubleContributors(unicDoubleContributors.size());
        gitTable.setPercentContributors(gitTable.getCountContributors()/gitTable.getCountFollowers()*100);
        List<GitUser>users = gitUserRepository.findAllByFollowersGreaterThan(countFollowers);
        gitTable.setCountPopularFollowers(users.size());
        return gitTable;
    }
}
