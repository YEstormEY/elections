INSERT INTO parties (party_name,
                     party_description,
                     party_leader,
                     party_focus,
                     party_ideology,
                     party_logo)
VALUES ('VVD', 'Liberal party focusing on free markets and individual responsibility.', 'Dilan-Yesilgoz',
        'Economy, Business', 'Liberalism', '/assets/images/VVD_logo.png'),
       ('D66', 'Progressive party focusing on education and sustainability.', 'Sigrid-Kaag', 'Education, Climate',
        'Progressivism', '/assets/images/D66_logo.png'),
       ('PvdA', 'Labour party focused on social equality and welfare.', 'Attje-Kuiken', 'Social Equality, Welfare',
        'Social Democracy', '/assets/images/pvda_logo.png'),
       ('GroenLinks', 'Green progressive party focusing on climate change and social justice.', 'Jesse-Klaver',
        'Climate, Social Justice', 'Green Politics', '/assets/images/groenlinks_logo.png'),
       ('CDA', 'Christian Democratic party focused on family values and social solidarity.', 'Aydin-Koca',
        'Family, Security', 'Christian Democracy', '/assets/images/cda_logo.png'),
       ('PVV', 'Right-wing nationalist party focusing on immigration and security.', 'Geert-Wilders',
        'Immigration, Nationalism', 'Nationalism', '/assets/images/pvv_logo.png'),
       ('SP', 'Socialist Party focusing on equality and welfare.', 'Aydin', 'Welfare, Social Justice', 'Socialism',
        '/assets/images/sp_logo.png'),
       ('FVD', 'Right-wing populist party focusing on sovereignty and nationalism.', 'Thierry-Baudet',
        'Sovereignty, Nationalism', 'Populism', '/assets/images/fvd_logo.png'),
       ('CU', 'ChristenUnie', 'Geert', 'Family, Christian Values', 'Christian Democracy',
        '/assets/images/BVNL_logo.png'),
       ('PvdD', 'Animal rights and environmental sustainability party.', 'Esther-Ouwehand',
        'Animal Rights, Environment', 'Animal Rights, Environmentalism', '/assets/images/50plus_logo.png'),
       ('SGP', 'Conservative Christian party with a focus on tradition and religion.', 'Kees', 'Tradition, Religion',
        'Conservatism', '/assets/images/sgp_logo.png'),
       ('Volt', 'Progressive pan-European party focused on unity and innovation.', 'Mam',
        'European Integration, Innovation', 'Pro-European, Progressivism', 'assets/images/logo_volt.png'),
       ('JA21', 'Juiste Antwoord 21', 'Joost-Eerdmans', 'Sovereignty, Law & Order', 'Liberal Conservatism',
        'assets/images/sp_logo.png'),
       ('BIJ1', 'Radical left-wing party focused on anti-capitalism and anti-racism.', 'Sylvana',
        'Anti-Racism, Equality', 'Radical Left', 'assets/images/bij1_logo.png'),
       ('BBB', 'Agrarian party focusing on farmers'' rights and rural development.', 'Kim',
        'Agriculture, Rural Development', 'Agrarianism', '/assets/images/bbb_logo.png'),
       ('DENK', 'Party focused on multiculturalism and inclusion.', 'Bart', 'Multiculturalism, Inclusion',
        'Multiculturalism', 'assets/images/denk_logo.png');

INSERT INTO users (username,
                   password,
                   email,
                   role)
VALUES ('admin', '$2a$10$VIDYEECU/UiuphTYd9RSJu.2qO4vwaKjDi.3inSELuQtVi7QlPwCe', 'admin@localhost', 'ADMIN'),
       ('user', '$2a$10$6/NqDD6zulczHRSOY7ybTOtv/Pih63BfWHOFpPJaWDruJkZw5ezae', 'user@localhost', 'USER');

-- Inserting articles with corrected syntax and escaping
INSERT INTO articles (title,
                      summary,
                      content,
                      image_path,
                      author_id)
VALUES ('The Future of AI in Healthcare',
        'AI is transforming healthcare by enabling personalized treatments and improving patient outcomes.',
        'Artificial Intelligence (AI) is rapidly becoming an essential tool in the healthcare industry. From diagnostics to treatment planning, AI algorithms are helping healthcare professionals make more accurate decisions...',
        'http://localhost:8080/img/ai_healthcare.jpg',
        1),

       ('Climate Change and Global Policies',
        'An overview of the global policies aimed at combating climate change and their effectiveness.',
        'Climate change remains one of the most pressing challenges of our time. Governments worldwide have introduced policies aimed at reducing carbon emissions, but are these measures enough to slow the global temperature rise?',
        'http://localhost:8080/img/klimaatverandering.jpg',
        2),

       ('The Netherlands and EU Agricultural Policies',
        'A look at how EU agricultural policies impact Dutch farmers.',
        'The Common Agricultural Policy (CAP) of the European Union has had significant effects on Dutch farmers. This article explores how these policies shape farming practices, subsidies, and sustainability in the Netherlands...',
        'http://localhost:8080/img/eu_agriculture.jpg', 1),

       ('The Future of Renewable Energy in the Netherlands',
        'How the Netherlands is transitioning to renewable energy sources.',
        'As the Netherlands works toward its climate goals, renewable energy plays a crucial role. This article examines the development of wind and solar power, government incentives, and the challenges that remain...',
        'http://localhost:8080/img/renewable_energy_netherlands.jpg', 1),

       ('Brexit: Impact on the Dutch Economy',
        'How Brexit has influenced trade and economic relations between the UK and the Netherlands.',
        'The UK’s exit from the European Union has created significant shifts in economic relations. This article discusses the impact of Brexit on Dutch businesses, trade agreements, and the future of economic cooperation...',
        'http://localhost:8080/img/brexit_dutch_economy.jpg', 1),

       ('Housing Crisis in the Netherlands',
        'The challenges of affordable housing in the Netherlands and government efforts to address the crisis.',
        'With growing demand and limited supply, the Netherlands faces a housing crisis. This article looks at government policies, market trends, and potential solutions to make housing more affordable...',
        'http://localhost:8080/img/housing_crisis.jpg', 1),

       ('EU Climate Policies and Their Effects on Dutch Industry',
        'How EU-wide climate policies are reshaping industries in the Netherlands.',
        'The European Union’s ambitious climate targets are pushing Dutch industries to reduce emissions. This article explores the challenges and opportunities faced by Dutch companies in complying with these regulations...',
        'http://localhost:8080/img/eu_climate_policies.jpg', 1),

       ('The Role of the Netherlands in EU Defense Cooperation',
        'Exploring the Netherlands'' participation in EU defense initiatives.',
        'The Netherlands has been actively involved in the EU’s efforts to strengthen defense cooperation. This article examines the country’s role in joint military projects, peacekeeping missions, and strategic initiatives within the EU...',
        'http://localhost:8080/img/eu_defense.jpg', 1);

-- Inserting categories with corrected formatting
INSERT INTO categories (name)
VALUES ('Politics'),
       ('Elections'),
       ('Healthcare'),
       ('Environment');

-- Inserting article-category associations (ensuring correct IDs)
INSERT INTO article_categories (article_id, category_id)
VALUES (3, 1), -- "The Netherlands and EU Agricultural Policies" linked to "Politics"
       (4, 4), -- "The Future of Renewable Energy in the Netherlands" linked to "Environment"
       (5, 1), -- "Brexit: Impact on the Dutch Economy" linked to "Politics"
       (6, 1), -- "Housing Crisis in the Netherlands" linked to "Politics"
       (7, 4), -- "EU Climate Policies and Their Effects on Dutch Industry" linked to "Environment"
       (8, 1); -- "The Role of the Netherlands in EU Defense Cooperation" linked to "Politics"

INSERT INTO comments (article_id, user_id, content)
VALUES
    (1, 1, 'Great insights on the future of AI in healthcare! I believe this technology will revolutionize the industry.'),
    (1, 2, 'AI in healthcare does raise some ethical concerns, though. We need to ensure patient privacy is protected.'),
    (2, 1, 'The article provides a good overview of global climate policies, but it would be interesting to see more local perspectives.'),
    (3, 2, 'EU agricultural policies have both positive and negative effects on Dutch farmers. It is a complex issue.'),
    (4, 1, 'Renewable energy is definitely the future, but there are still many challenges ahead, especially regarding storage.'),
    (5, 2, 'Brexit has been a disaster for both the UK and the Netherlands in terms of trade. More needs to be done to mitigate the impact.'),
    (6, 1, 'The housing crisis is a pressing issue that requires immediate action. Government policies should focus on affordable housing.'),
    (7, 2, 'Dutch industries are facing tough choices with the new EU climate policies. Balancing regulations with economic growth is crucial.');

INSERT INTO article_likes (article_id, user_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 2),
       (4, 1),
       (5, 2),
       (6, 1),
       (7, 2);
