SELECT id INTO @adminId FROM users WHERE username = 'admin';
SELECT id INTO @categoryId FROM category WHERE category_name = 'General Knowledge';


-- Quiz 1
INSERT INTO quiz
(id, title, description, category_id, is_public, creator_id, difficulty) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000002001', '-','')), 'Basic Math Quiz', 'A quiz covering basic arithmetic operations.', @categoryId, true, @adminId, 'EASY');


-- Question 1
INSERT INTO question
(id, quiz_id, text, question_type, creation_date) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000003001', '-','')), UNHEX(REPLACE('00000000-0000-0000-0000-000000002001', '-','')), 'What is 2 + 2?', 'MULTIPLE_CHOICE', NOW());


-- Correct Answer
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004001', '-','')), '4', true, UNHEX(REPLACE('00000000-0000-0000-0000-000000003001', '-','')));

-- Incorrect Answer
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004002', '-','')), '3', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003001', '-','')));


-- Question 2
INSERT INTO question
(id, quiz_id, text, question_type, creation_date) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000003002', '-','')), UNHEX(REPLACE('00000000-0000-0000-0000-000000002001', '-','')), 'What is the square root of 16?', 'MULTIPLE_CHOICE', NOW());

-- Correct Answer: 4
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004003', '-','')), '4', true, UNHEX(REPLACE('00000000-0000-0000-0000-000000003002', '-','')));

-- Incorrect Answer: 6
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004004', '-','')), '6', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003002', '-','')));

-- Incorrect Answer: 3
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004005', '-','')), '3', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003002', '-','')));

-- Incorrect Answer: 8
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004006', '-','')), '8', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003002', '-','')));



-- Quiz 2

INSERT INTO quiz
(id, title, description, category_id, is_public, creator_id, difficulty) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000002002', '-','')), 'Basic Science Quiz', 'A quiz covering basic science concepts.', @categoryId, true, @adminId, 'EASY');


INSERT INTO question
(id, quiz_id, text, question_type, creation_date) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000003003', '-','')), UNHEX(REPLACE('00000000-0000-0000-0000-000000002002', '-','')), 'What is the boiling point of water?', 'MULTIPLE_CHOICE', NOW());

-- Correct Answer: 100°C
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004007', '-','')), '100°C', true, UNHEX(REPLACE('00000000-0000-0000-0000-000000003003', '-','')));

-- Incorrect Answers
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004008', '-','')), '90°C', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003003', '-',''))),
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004009', '-','')), '120°C', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003003', '-','')));


INSERT INTO question
(id, quiz_id, text, question_type, creation_date) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000003004', '-','')), UNHEX(REPLACE('00000000-0000-0000-0000-000000002002', '-','')), 'What gas is necessary for photosynthesis?', 'MULTIPLE_CHOICE', NOW());


-- Correct Answer: Carbon Dioxide
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004010', '-','')), 'Carbon Dioxide', true, UNHEX(REPLACE('00000000-0000-0000-0000-000000003004', '-','')));

-- Incorrect Answers
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004011', '-','')), 'Oxygen', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003004', '-',''))),
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004012', '-','')), 'Nitrogen', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003004', '-','')));


-- Quiz 3
INSERT INTO quiz
(id, title, description, category_id, is_public, creator_id, difficulty) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000002003', '-','')), 'Basic Geography Quiz', 'A quiz covering basic geography knowledge.', @categoryId, true, @adminId, 'EASY');

INSERT INTO question
(id, quiz_id, text, question_type, creation_date) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000003005', '-','')), UNHEX(REPLACE('00000000-0000-0000-0000-000000002003', '-','')), 'What is the largest continent?', 'MULTIPLE_CHOICE', NOW());


-- Correct Answer: Asia
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004013', '-','')), 'Asia', true, UNHEX(REPLACE('00000000-0000-0000-0000-000000003005', '-','')));

-- Incorrect Answers
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004014', '-','')), 'Africa', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003005', '-',''))),
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004015', '-','')), 'Antarctica', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003005', '-','')));


INSERT INTO question
(id, quiz_id, text, question_type, creation_date) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000003006', '-','')), UNHEX(REPLACE('00000000-0000-0000-0000-000000002003', '-','')), 'What is the longest river in the world?', 'MULTIPLE_CHOICE', NOW());


-- Correct Answer: Nile
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
    (UNHEX(REPLACE('00000000-0000-0000-0000-000000004016', '-','')), 'Nile', true, UNHEX(REPLACE('00000000-0000-0000-0000-000000003006', '-','')));

-- Incorrect Answers
INSERT INTO answer
(id, text, is_correct, question_id) VALUES
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004017', '-','')), 'Amazon', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003006', '-',''))),
                                        (UNHEX(REPLACE('00000000-0000-0000-0000-000000004018', '-','')), 'Yangtze', false, UNHEX(REPLACE('00000000-0000-0000-0000-000000003006', '-','')));


