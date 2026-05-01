-- Insert sample venues
INSERT INTO venues (name, address, location, capacity) VALUES
('Madison Square Garden', '4 Pennsylvania Plaza, New York, NY 10001', 'New York', 20789),
('Hollywood Bowl', '2301 N Highland Ave, Los Angeles, CA 90068', 'Los Angeles', 17500),
('Royal Albert Hall', 'Kensington Gore, South Kensington, London SW7 2AP', 'London', 5272),
('Sydney Opera House', 'Bennelong Point, Sydney NSW 2000', 'Sydney', 2679);

-- Insert sample artists
INSERT INTO artists (name, bio, image_url) VALUES
('Taylor Swift', 'American singer-songwriter known for narrative songwriting', 'https://example.com/taylor-swift.jpg'),
('Ed Sheeran', 'English singer-songwriter and musician', 'https://example.com/ed-sheeran.jpg'),
('Billie Eilish', 'American singer-songwriter and musician', 'https://example.com/billie-eilish.jpg'),
('The Weeknd', 'Canadian singer-songwriter and record producer', 'https://example.com/the-weeknd.jpg'),
('Ariana Grande', 'American singer, songwriter, and actress', 'https://example.com/ariana-grande.jpg');

-- Insert sample users
INSERT INTO users (name, email, password, role) VALUES
('John Doe', 'john.doe@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ROLE_USER'),
('Jane Smith', 'jane.smith@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ROLE_USER'),
('Admin User', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ROLE_ADMIN');

-- Insert sample events
INSERT INTO events (name, description, event_date, category, image_url, venue_id) VALUES
('Taylor Swift Concert', 'An amazing concert by Taylor Swift featuring her latest hits', '2024-06-15 20:00:00', 'Concert', 'https://example.com/taylor-concert.jpg', 1),
('Ed Sheeran Live', 'Intimate acoustic performance by Ed Sheeran', '2024-07-20 19:30:00', 'Concert', 'https://example.com/ed-concert.jpg', 2),
('Billie Eilish World Tour', 'Billie Eilish performing songs from her latest album', '2024-08-10 21:00:00', 'Concert', 'https://example.com/billie-concert.jpg', 3),
('The Weeknd After Hours', 'The Weeknd performing his chart-topping hits', '2024-09-05 20:30:00', 'Concert', 'https://example.com/weeknd-concert.jpg', 4),
('Ariana Grande Sweetener Tour', 'Ariana Grande with special guests', '2024-10-12 20:00:00', 'Concert', 'https://example.com/ariana-concert.jpg', 1);

-- Insert event-artist relationships
INSERT INTO event_artists (event_id, artist_id) VALUES
(1, 1), -- Taylor Swift Concert - Taylor Swift
(2, 2), -- Ed Sheeran Live - Ed Sheeran
(3, 3), -- Billie Eilish World Tour - Billie Eilish
(4, 4), -- The Weeknd After Hours - The Weeknd
(5, 5); -- Ariana Grande Sweetener Tour - Ariana Grande

-- Insert sample bookings
INSERT INTO bookings (booking_date, user_id, event_id, price) VALUES
('2024-01-15 10:30:00', 1, 1, 150.00),
('2024-01-16 14:20:00', 2, 2, 120.00),
('2024-01-17 09:15:00', 1, 3, 200.00);

-- Insert sample tickets
INSERT INTO tickets (seat_no, booking_id) VALUES
(1, 1), (2, 1), (3, 1), -- 3 tickets for booking 1
(10, 2), (11, 2), -- 2 tickets for booking 2
(25, 3), (26, 3), (27, 3), (28, 3); -- 4 tickets for booking 3
