package com.example.devrel.model;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gitusers")
public class GitUser {
    @Column
    private String login;
    @Id
    @Column
    private String id;
    @Column
    private String url;
    @Column
    private String html_url;
    @Column
    private String followers_url;
    @Column
    private String following_url;
    @Column
    private String gists_url;
    @Column
    private String starred_url;
    @Column
    private String subscriptions_url;
    @Column
    private String organizations_url;
    @Column
    private String repos_url;
    @Column
    private String events_url;
    @Column
    private String received_events_url;
    @Column
    private String type;
    @Column
    private String site_admin;
    @Column
    private String name;
    @Column
    private String company;
    @Column
    private String location;
    @Column
    private String email;
    @Column
    private Integer public_repos;
    @Column
    private Integer public_gists;
    @Column
    private Integer followers;
    @Column
    private Integer following;
    @Column
    private LocalDateTime created_at;
    @Column
    private LocalDateTime updated_at;

}
