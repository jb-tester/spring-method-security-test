USE jbtests;

INSERT INTO contact (firstname, lastname, telephone, email) VALUES
    ('irina',   'petrovskaya',   '111-1111', 'ip@example.com'),
    ('andrey',     'ivanov',   '111-2222', 'ai@example.com'),
    ('vera', 'ivanova',   '111-3333', 'vi@example.com'),
    ('will',   'johnston',  '111-4444', 'wj@example.com'),
    ('ekaterina',     'alekseeva', '111-5555', 'ea@example.com');

INSERT INTO sample (version, sample, color) VALUES
    (1, 'alpha',   'red'),
    (2, 'alpha',   'red'),
    (1, 'beta',    'blue'),
    (2, 'gamma',   'green'),
    (2, 'delta',   'yellow'),
    (3, 'omega',   'green'),
    (3, 'epsilon', 'purple');
