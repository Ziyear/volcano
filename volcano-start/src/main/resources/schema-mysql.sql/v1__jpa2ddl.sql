
    create table volcano.volcano_permissions (
       id bigint not null auto_increment,
        displayName varchar(50) not null,
        permissionCode varchar(50) not null,
        primary key (id)
    ) engine=InnoDB;

    create table volcano.volcano_roles (
       id bigint not null auto_increment,
        built_in bit not null,
        displayName varchar(50) not null,
        roleCode varchar(50) not null,
        roleName varchar(50) not null,
        primary key (id)
    ) engine=InnoDB;

    create table volcano.volcano_roles_permissions (
       role_id bigint not null,
        permission_id bigint not null,
        primary key (role_id, permission_id)
    ) engine=InnoDB;

    create table volcano.volcano_users (
       id bigint not null auto_increment,
        accountNonExpired bit not null,
        accountNonLocked bit not null,
        credentialsNonExpired bit not null,
        email varchar(254) not null,
        enabled bit not null,
        mfa_key varchar(255) not null,
        mobile varchar(11) not null,
        name varchar(50) not null,
        password_hash varchar(80) not null,
        username varchar(50) not null,
        using_mfa bit not null,
        primary key (id)
    ) engine=InnoDB;

    create table volcano.volcano_users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) engine=InnoDB;

    alter table volcano.volcano_permissions 
       drop index UK_1idty70cua9wgm82q80sr3xof;

    alter table volcano.volcano_permissions 
       add constraint UK_1idty70cua9wgm82q80sr3xof unique (displayName);

    alter table volcano.volcano_roles 
       drop index UK_8expsyu7spxp1wtcpqsma59jh;

    alter table volcano.volcano_roles 
       add constraint UK_8expsyu7spxp1wtcpqsma59jh unique (roleCode);

    alter table volcano.volcano_roles 
       drop index UK_t61cvkauocuw64mpdo0x72y4q;

    alter table volcano.volcano_roles 
       add constraint UK_t61cvkauocuw64mpdo0x72y4q unique (roleName);

    alter table volcano.volcano_users 
       drop index UK_miktqqu0rp4ud1ign7im14tls;

    alter table volcano.volcano_users 
       add constraint UK_miktqqu0rp4ud1ign7im14tls unique (email);

    alter table volcano.volcano_users 
       drop index UK_2oxvufns78dymbl7l58dcy1c4;

    alter table volcano.volcano_users 
       add constraint UK_2oxvufns78dymbl7l58dcy1c4 unique (mobile);

    alter table volcano.volcano_users 
       drop index UK_tnc0dltyfewuu3yq4fex72map;

    alter table volcano.volcano_users 
       add constraint UK_tnc0dltyfewuu3yq4fex72map unique (username);

    alter table volcano.volcano_roles_permissions 
       add constraint FK5lmqouhtttuvgb6bmsxa17mug 
       foreign key (permission_id) 
       references volcano.volcano_permissions (id);

    alter table volcano.volcano_roles_permissions 
       add constraint FKt92j6hp5x69my9o68w938euc8 
       foreign key (role_id) 
       references volcano.volcano_roles (id);

    alter table volcano.volcano_users_roles 
       add constraint FKh2kameocdq0sire4vajm0h45g 
       foreign key (role_id) 
       references volcano.volcano_roles (id);

    alter table volcano.volcano_users_roles 
       add constraint FK4yletyn0k3f1fhosd7wym8pbs 
       foreign key (user_id) 
       references volcano.volcano_users (id);
