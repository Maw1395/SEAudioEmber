from firebase import firebase
from app import db, models
from datetime import datetime

firebase = firebase.FirebaseApplication('https://seaudioember.firebaseio.com/', None)

SONG_MAX = db.session.query(models.songs).order_by(models.songs.SongID.desc()).first().SongID
GenreList=['hot-100','country-songs', 'rock-songs','r-b-hip-hop-songs','dance-club-play-songs','pop-songs']
for i in range(SONG_MAX):
    print (i)
    songEntry = i
    for j in range(len(GenreList)):
        IndividualSongList = db.session.query(models.songs).filter(models.songs.SongID==songEntry,
                models.songs.Genre==GenreList[j])
        if IndividualSongList!=None:
            for IndividualSongEntry in IndividualSongList:
                Date= IndividualSongEntry.Date
                Points = IndividualSongEntry.Points
                Artist = IndividualSongEntry.Artist
                Title = IndividualSongEntry.Title
                SongID = IndividualSongEntry.SongID
                path = "SongByDay/" + str(SongID) + '/' +GenreList[j] +'/' + Date.strftime('%Y-%m-%d') +'/'
                firebase.post(path, {str(Title), str(Artist), str(Points)} )



