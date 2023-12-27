create table responses(

    id bigint not null auto_increment,
    text text not null,
    solution tinyint not null,
    post_id bigint not null,
    user_id bigint not null,
    response_date datetime not null,

    primary key(id),

    constraint fk_responses_post_id foreign key(post_id) references posts(id),
    constraint fk_responses_user_id foreign key(user_id) references users(id)

);