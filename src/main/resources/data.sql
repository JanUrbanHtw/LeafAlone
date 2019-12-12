insert into leaf_alone_user (id, username, password, role)
values (1, 'user', '$2a$10$WrOoBjGqVDd4cCBxmpeMBO2zYVEeT0ixssruKpITP9ViF4aBDDqk2', 'ROLE_USER');

insert into leaf_alone_user (id, username, password, role)
values (2, 'contributor', '$2a$10$WTnBAWaow./4WdtQRtYpBOjQ5dho3r4njRyM2nswWABcIr.BBXiCy', 'ROLE_CONTRIBUTOR');

insert into plant_care (id, colloquial, scientific, sun_situation, water_cycle,
                        water_amount, soil_advice, description, contributor_id)
values (1, 'Jade Plant', 'Crassula Ovata', 0,
        5, 20,
        'soil specifically for succulents would be great, but anything works really',
        'A nice beginner plant. Very forgiving and somewhat resistant to damage of any kind',
        2);

insert into plant_care (id, colloquial, scientific, sun_situation, water_cycle,
                        water_amount, soil_advice, description, contributor_id)
values (2, 'Bunny-Ears Cactus', 'Opuntia Microdasys', 0,
        10, 15,
        'succulent- or cactus-specific soil recommended',
        'A classy cactus type. But beware: cacti are not as resistant to bad care as often believed!',
        2);


-- SunSituation sunSituation, @Min(1) int waterCycle, @Min(1) int waterAmount, String soilAdvice, String description, LeafAloneUser contributor

-- insert into leafAloneUser
-- values(10003,'test', 'test');
