from firebase import firebase
from app import db, models
from sqlalchemy import exists, desc

firebase = firebase.FirebaseApplication('https://audioember-65fad.firebaseio.com/', None)

sid = 0
SONG_MAX = 2123
ARTIST_MAX = 128
SONG_MAX = 1

for i in range(SONG_MAX):
    s = db.session.query(models.songs).filter(models.songs.SongID == sid).all()
    for song_entry in s:
        points = db.session.query(models.song_points).filter(models.song_points.SongID == sid, models.song_points.Genre == s.Genre).first().Points
        path = "/" + song_entry.Genre  + "/" + song_entry.Artist + "/" + song_entry.Title + "/"
        result = firebase.post(path, points)
        print result
        '''
        path = "/" + song_entry.Genre  + "/" + song_entry.Artist + "/" + song_entry.Title + "/graphs/" 
        urls = db.session.query(models.graphs).filter(models.graphs.SongID == sid).all()
        for u in urls:
            result = firebase.post(path, u.URL)
            print result
        '''
