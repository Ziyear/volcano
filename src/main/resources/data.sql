insert into volcano_users(id, username, `name`, mobile, password_hash, enabled, account_non_expired, account_non_locked, credentials_non_expired, email)
values (1, 'user', 'Zhang San', '13000000001', '{bcrypt}$2a$10$jhS817qUHgOR4uQSoEBRxO58.rZ1dBCmCTjG8PeuQAX4eISf.zowm', 1, 1, 1, 1, 'zhangsan@local.dev'),
       (2, 'old_user', 'Li Si', '13000000002', '{SHA-1}{TMlrFVppiMOhmI6VBoytlEkepfqUHrpyOXgCoFUo3Mk=}1ebde6bb35fd02816880948864fa771eb85a122a', 1, 1, 1, 1, 'lisi@local.dev');
insert into volcano_roles(id, role_name) values (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
insert into volcano_users_roles(user_id, role_id) values (1, 1), (1, 2), (2, 1);