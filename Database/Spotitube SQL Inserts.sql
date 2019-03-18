USE master

USE Spotitube

INSERT INTO [USER] VALUES ('Lars', '314156yhrsfad-a', '909090')
INSERT INTO [USER] VALUES ('Test', '1312214-ADOIJWNF-23', '12345678')

INSERT INTO PLAYLIST VALUES(1,'Lars', 'Death Metal' , 1) 
INSERT INTO PLAYLIST VALUES(2,'Lars', 'Pop', 0)

INSERT INTO TRACK (TRACK_ID, TITLE, PERFORMER, DURATION, ALBUM, OFFLINEAVAILABLE)
VALUES (1, 'Song for Someone', 'The Frames', 350, 'The cost', 0)

INSERT INTO TRACK (TRACK_ID, TITLE, PERFORMER, DURATION, PLAYCOUNT, PUBLICATIONDATE, DESCRIPTION, OFFLINEAVAILABLE)
VALUES (2, 'The Cost', 'The frames', 423, 37, '10-01-2005', 'Title song from the Album The Cost', 1)

INSERT INTO TRACK_IN_PLAYLIST
VALUES(1,1),(2,1)