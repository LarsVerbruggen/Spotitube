/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2014                    */
/* Created on:     15-3-2019 10:44:56                           */
/*==============================================================*/
USE master

DROP DATABASE IF EXISTS Spotitube
GO

CREATE DATABASE Spotitube
GO

USE Spotitube

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('PLAYLIST') and o.name = 'FK_PLAYLIST_USER_MADE_USER')
alter table PLAYLIST
   drop constraint FK_PLAYLIST_USER_MADE_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TRACK_IN_PLAYLIST') and o.name = 'FK_TRACK_IN_TRACK_IN__TRACK')
alter table TRACK_IN_PLAYLIST
   drop constraint FK_TRACK_IN_TRACK_IN__TRACK
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TRACK_IN_PLAYLIST') and o.name = 'FK_TRACK_IN_TRACK_IN__PLAYLIST')
alter table TRACK_IN_PLAYLIST
   drop constraint FK_TRACK_IN_TRACK_IN__PLAYLIST
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('PLAYLIST')
            and   name  = 'USER_MADE_PLAYLIST_FK'
            and   indid > 0
            and   indid < 255)
   drop index PLAYLIST.USER_MADE_PLAYLIST_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PLAYLIST')
            and   type = 'U')
   drop table PLAYLIST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TRACK')
            and   type = 'U')
   drop table TRACK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TRACK_IN_PLAYLIST')
            and   name  = 'TRACK_IN_PLAYLIST2_FK'
            and   indid > 0
            and   indid < 255)
   drop index TRACK_IN_PLAYLIST.TRACK_IN_PLAYLIST2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TRACK_IN_PLAYLIST')
            and   name  = 'TRACK_IN_PLAYLIST_FK'
            and   indid > 0
            and   indid < 255)
   drop index TRACK_IN_PLAYLIST.TRACK_IN_PLAYLIST_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TRACK_IN_PLAYLIST')
            and   type = 'U')
   drop table TRACK_IN_PLAYLIST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('"USER"')
            and   type = 'U')
   drop table "USER"
go

if exists(select 1 from systypes where name='ALBUM')
   drop type ALBUM
go

if exists(select 1 from systypes where name='DESCRIPTION')
   drop type DESCRIPTION
go

if exists(select 1 from systypes where name='DURATION')
   drop type DURATION
go

if exists(select 1 from systypes where name='ID')
   drop type ID
go

if exists(select 1 from systypes where name='NAME')
   drop type NAME
go

if exists(select 1 from systypes where name='OFFLINEAVAILABLE')
   drop type OFFLINEAVAILABLE
go

if exists(select 1 from systypes where name='OWNER')
   drop type OWNER
go

if exists(select 1 from systypes where name='PASSWORD')
   drop type PASSWORD
go

if exists(select 1 from systypes where name='PERFORMER')
   drop type PERFORMER
go

if exists(select 1 from systypes where name='PLAYCOUNT')
   drop type PLAYCOUNT
go

if exists(select 1 from systypes where name='PUBLICATIONDATE')
   drop type PUBLICATIONDATE
go

if exists(select 1 from systypes where name='TITLE')
   drop type TITLE
go

if exists(select 1 from systypes where name='TOKEN')
   drop type TOKEN
go

/*==============================================================*/
/* Domain: ALBUM                                                */
/*==============================================================*/
create type ALBUM
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: DESCRIPTION                                          */
/*==============================================================*/
create type DESCRIPTION
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: DURATION                                             */
/*==============================================================*/
create type DURATION
   from int
go

/*==============================================================*/
/* Domain: ID                                                   */
/*==============================================================*/
create type ID
   from int
go

/*==============================================================*/
/* Domain: NAME                                                 */
/*==============================================================*/
create type NAME
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: OFFLINEAVAILABLE                                     */
/*==============================================================*/
create type OFFLINEAVAILABLE
   from bit
go

/*==============================================================*/
/* Domain: OWNER                                                */
/*==============================================================*/
create type OWNER
   from bit
go

/*==============================================================*/
/* Domain: PASSWORD                                             */
/*==============================================================*/
create type PASSWORD
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: PERFORMER                                            */
/*==============================================================*/
create type PERFORMER
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: PLAYCOUNT                                            */
/*==============================================================*/
create type PLAYCOUNT
   from int
go

/*==============================================================*/
/* Domain: PUBLICATIONDATE                                      */
/*==============================================================*/
create type PUBLICATIONDATE
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: TITLE                                                */
/*==============================================================*/
create type TITLE
   from nvarchar(1024)
go

/*==============================================================*/
/* Domain: TOKEN                                                */
/*==============================================================*/
create type TOKEN
   from nvarchar(1024)
go

/*==============================================================*/
/* Table: PLAYLIST                                              */
/*==============================================================*/
create table PLAYLIST (
   PLAYLIST_ID         ID                   not null,
   USER_NAME            NAME                 null,
   PLAYLIST_NAME        NAME                 null,
   OWNER                OWNER                null,
   constraint PK_PLAYLIST primary key (PLAYLIST_ID)
)
go

/*==============================================================*/
/* Index: USER_MADE_PLAYLIST_FK                                 */
/*==============================================================*/




create nonclustered index USER_MADE_PLAYLIST_FK on PLAYLIST (USER_NAME ASC)
go

/*==============================================================*/
/* Table: TRACK                                                 */
/*==============================================================*/
create table TRACK (
   TRACK_ID             ID                   not null,
   TITLE                TITLE                null,
   PERFORMER            PERFORMER            null,
   DURATION             DURATION             null,
   ALBUM                ALBUM                null,
   PLAYCOUNT            PLAYCOUNT            null,
   PUBLICATIONDATE      PUBLICATIONDATE      null,
   DESCRIPTION          DESCRIPTION          null,
   OFFLINEAVAILABLE     OFFLINEAVAILABLE     null,
   constraint PK_TRACK primary key (TRACK_ID)
)
go

/*==============================================================*/
/* Table: TRACK_IN_PLAYLIST                                     */
/*==============================================================*/
create table TRACK_IN_PLAYLIST (
   TRACK_ID             ID                   not null,
   PLAYLIST_ID         ID                   not null,
   constraint PK_TRACK_IN_PLAYLIST primary key (TRACK_ID, PLAYLIST_ID)
)
go

/*==============================================================*/
/* Index: TRACK_IN_PLAYLIST_FK                                  */
/*==============================================================*/




create nonclustered index TRACK_IN_PLAYLIST_FK on TRACK_IN_PLAYLIST (TRACK_ID ASC)
go

/*==============================================================*/
/* Index: TRACK_IN_PLAYLIST2_FK                                 */
/*==============================================================*/




create nonclustered index TRACK_IN_PLAYLIST2_FK on TRACK_IN_PLAYLIST (PLAYLIST_ID ASC)
go

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   USER_NAME            NAME                 not null,
   TOKEN                TOKEN                null,
   PASSWORD             PASSWORD             null,
   constraint PK_USER primary key (USER_NAME)
)
go

alter table PLAYLIST
   add constraint FK_PLAYLIST_USER_MADE_USER foreign key (USER_NAME)
      references "USER" (USER_NAME)
go

alter table TRACK_IN_PLAYLIST
   add constraint FK_TRACK_IN_TRACK_IN__TRACK foreign key (TRACK_ID)
      references TRACK (TRACK_ID)
go

alter table TRACK_IN_PLAYLIST
   add constraint FK_TRACK_IN_TRACK_IN__PLAYLIST foreign key (PLAYLIST_ID)
      references PLAYLIST (PLAYLIST_ID)
go

