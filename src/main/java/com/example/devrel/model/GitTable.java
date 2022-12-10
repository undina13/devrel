package com.example.devrel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GitTable {
    //	Название/урл сообщества
    private String name;

    //	Количество фоловеров
    private Integer countFollowers;

    //	Количество репозиториев/проектов
    private Integer countRepo;

    //	Количество контрибьюторов уникальных (т. е. Если чел контрибьютил в 2-3 репозитория сообщества, считался он как один контрибьютор)
    private Integer countContributors;

    //	Количество контрибьюторов сразу в нескольких репозиториях (т. е. Сразу в несколько репо коммитил, а не только одним занимался)
    private Integer countDoubleContributors;

    //	% фоловеров среди контрибьюторов
    private Integer percentContributors;

    //	Количество популярных фоловеров (фоловеры, имеющие не менее 20 собственных фоловеров)
    private Integer countPopularFollowers;

}
