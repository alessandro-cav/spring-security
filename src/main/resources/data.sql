
   INSERT INTO tb_user ( email, nome, "role", senha ,sobrenome)
 SELECT  'root@gmail.com','ale','USER','$2a$12$W4ga0wBDtSMXGY0y41/PAuY8o0E.SGucCpddVRY6gvZhPxA16bpGi','oliveira'
    WHERE NOT EXISTS (SELECT email FROM tb_user WHERE id  = 1);