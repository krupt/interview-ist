DO $$
DECLARE
  vChiefEmployeeId   BIGINT;
  vManagerEmployeeId BIGINT;
  vUserEmployeeId    BIGINT;
  vAdminEmployeeId   BIGINT;
  vUserId            BIGINT;
  vManagerId         BIGINT;
  vChiefId           BIGINT;
  vAdminId           BIGINT;
  vUserRoleId        BIGINT;
  vManagerRoleId     BIGINT;
  vChiefRoleId       BIGINT;
  vAdminRoleId       BIGINT;
BEGIN
  INSERT INTO t_employee(description)
  VALUES ('Администратор')
  RETURNING id
    INTO vAdminEmployeeId;
  INSERT INTO t_employee(description)
  VALUES ('Руководитель 1')
  RETURNING id
    INTO vChiefEmployeeId;
  INSERT INTO t_employee(description, chief_id)
  VALUES ('Менеджер 1', vChiefEmployeeId)
  RETURNING id
    INTO vManagerEmployeeId;
  -- У пользователя можно не задавать руководителя, но пусть будет :)
  INSERT INTO t_employee(description, chief_id)
  VALUES ('Пользователь 1', vChiefEmployeeId)
  RETURNING id
    INTO vUserEmployeeId;

  -- Для генерации паролей можно воспользоваться src/test/java/ru/ist/security/GeneratePasswordTest.java
  INSERT INTO t_user(id, name, password, enabled)
  VALUES (0, 'system', 'lkp19u-m0jn8-bc87-n0a98w7brc098a7b-4asdas214343n32knga5-gfdk', false);
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('admin', '$2a$10$yxC79ObszWp6uHKAMxj6jOF.eUubaWmM4R22MFflNpzv1bMlt1KMG', true, vAdminEmployeeId)
  RETURNING id
    INTO vAdminId;
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('user', '$2a$10$aESOnI5MYbcqtOQsTBQkeOFYHYFRxVfknrbjsLuTzfRiZVq8ymeE.', true, vUserEmployeeId)
  RETURNING id
    INTO vUserId;
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('manager', '$2a$10$07CTeDQiRKFyojDwl3u.M.U4XEeR0QQ1MBHnyEBoeLEmSW6S/cfEi', true, vManagerEmployeeId)
  RETURNING id
    INTO vManagerId;
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('chief', '$2a$10$.YnDbePGu3FH2.XkLRJ3jed.CpAxH1kWGu7B3o3Wu.c0k6hEcI9Rq', true, vChiefEmployeeId)
  RETURNING id
    INTO vChiefId;

  INSERT INTO t_role(name, description)
  VALUES('ADMIN', 'Администраторы')
  RETURNING id
    INTO vAdminRoleId;
  INSERT INTO t_role(name, description)
  VALUES('USER', 'Пользователи')
  RETURNING id
    INTO vUserRoleId;
  INSERT INTO t_role(name, description)
  VALUES('MANAGER', 'Менеджеры')
  RETURNING id
    INTO vManagerRoleId;
  INSERT INTO t_role(name, description)
  VALUES('CHIEF', 'Руководители')
  RETURNING id
    INTO vChiefRoleId;

  INSERT INTO t_user2role(user_id, role_id)
  VALUES (vAdminId, vAdminRoleId),
         (vUserId, vUserRoleId),
         (vManagerId, vManagerRoleId),
         (vChiefId, vChiefRoleId);

  INSERT INTO t_payment(description, create_date, state, created_user_id, accepted_user_id)
  VALUES ('Первый платеж', current_timestamp, 'NEW', vUserId, null),
         ('Платеж за аренду', current_timestamp, 'NEW', vUserId, null),
         ('Платеж за оргтехнику', current_timestamp, 'ACCEPTED', vUserId, vManagerId),
         ('Платеж за интернет', current_timestamp, 'ACCEPTED', vUserId, vManagerId),
         ('Платеж за комунальные услуги', current_timestamp, 'DECLINED', vUserId, vManagerId),
         ('Выплата по договору IT-консалтинга', current_timestamp, 'COMPLETED', vUserId, vManagerId),
         ('Выплата премии', current_timestamp, 'COMPLETED', vUserId, vManagerId),
         ('Оплата корпоратива', current_timestamp, 'DECLINED', vUserId, vManagerId);


  INSERT INTO t_employee(description)
  VALUES ('Руководитель 2')
  RETURNING id
    INTO vChiefEmployeeId;
  INSERT INTO t_employee(description, chief_id)
  VALUES ('Менеджер 2', vChiefEmployeeId)
  RETURNING id
    INTO vManagerEmployeeId;
  -- У пользователя можно не задавать руководителя, но пусть будет :)
  INSERT INTO t_employee(description, chief_id)
  VALUES ('Пользователь 2', vChiefEmployeeId)
  RETURNING id
    INTO vUserEmployeeId;

  -- Для генерации паролей можно воспользоваться src/test/java/ru/ist/security/GeneratePasswordTest.java
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('user2', '$2a$10$VkFQzNmGk4.je4qZRHFus.2TsSfxyAr2Pzmcly64WxE92ikZey4ou', true, vUserEmployeeId)
  RETURNING id
    INTO vUserId;
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('manager2', '$2a$10$C/pFeKLb9BF/nR6znE3y1ekK1m0XQ8/ES43tZS.fdBMzW8UIsmWRm', true, vManagerEmployeeId)
  RETURNING id
    INTO vManagerId;
  INSERT INTO t_user(name, password, enabled, employee_id)
  VALUES ('chief2', '$2a$10$gR23snlY1e2fU3vJ0CrvOuwfWXHrUi0iCFfulgizEG8cWZ3F3TH.K', true, vChiefEmployeeId)
  RETURNING id
    INTO vChiefId;

  INSERT INTO t_user2role(user_id, role_id)
  VALUES (vUserId, vUserRoleId),
    (vManagerId, vManagerRoleId),
    (vChiefId, vChiefRoleId);

  INSERT INTO t_payment(description, create_date, state, created_user_id, accepted_user_id)
  VALUES ('Второй платеж', current_timestamp, 'NEW', vUserId, null),
    ('Третий платеж за аренду', current_timestamp, 'NEW', vUserId, null),
    ('Четвертый платеж за оргтехнику', current_timestamp, 'ACCEPTED', vUserId, vManagerId),
    ('Пятый платеж за интернет', current_timestamp, 'ACCEPTED', vUserId, vManagerId),
    ('Шестой платеж за комунальные услуги', current_timestamp, 'DECLINED', vUserId, vManagerId),
    ('Седьмая выплата по договору IT-консалтинга', current_timestamp, 'COMPLETED', vUserId, vManagerId),
    ('Восьмая выплата премии', current_timestamp, 'COMPLETED', vUserId, vManagerId),
    ('Девятая оплата корпоратива', current_timestamp, 'DECLINED', vUserId, vManagerId),
    ('Первый кросс', current_timestamp, 'ACCEPTED', ( SELECT MIN(u.id)
                                                      FROM t_user u
                                                        JOIN t_user2role ur ON ur.user_id = u.id
                                                        JOIN t_role r ON r.id = ur.role_id
                                                                         AND r.name = 'USER'
    ), ( SELECT MAX(u.id)
         FROM t_user u
           JOIN t_user2role ur ON ur.user_id = u.id
           JOIN t_role r ON r.id = ur.role_id
                            AND r.name = 'MANAGER'
     )
    ),
    ('Второй кросс', current_timestamp, 'ACCEPTED', ( SELECT MAX(u.id)
                                                      FROM t_user u
                                                        JOIN t_user2role ur ON ur.user_id = u.id
                                                        JOIN t_role r ON r.id = ur.role_id
                                                                         AND r.name = 'USER'
    ), ( SELECT MIN(u.id)
         FROM t_user u
           JOIN t_user2role ur ON ur.user_id = u.id
           JOIN t_role r ON r.id = ur.role_id
                            AND r.name = 'MANAGER'
     )
    );
END $$;
