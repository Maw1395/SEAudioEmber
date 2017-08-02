from firebase import firebase
from app import db, models
from sqlalchemy import exists, desc
import plotly.tools as tls 

firebase = firebase.FirebaseApplication('https://audioember-65fad.firebaseio.com/', None)

sid = 1
ARTIST_MAX = 128
SONG_MAX = 223
SONG_MAX = 1633
SONG_MAX = 2123
CURRENT = 1 
URL_NUM = 0

# iterate over all songs in SQl database and add them to FireBase
for i in range(SONG_MAX):
    #print(i)
    sid = i + CURRENT
    s = db.session.query(models.songs).filter(models.songs.SongID == sid).all()
    #print(s)
    entry = [False,False,False,False,False,False]
    #iterate through all entries for each song and add necesary data to FireBase
    for song_entry in s:
        if not(song_entry.Genre == "hot-100" and entry[0] == True) and not (song_entry.Genre == "country-songs" and  entry[1] == True) and not (song_entry.Genre == "rock-songs" and entry[2] == True) and not (song_entry.Genre == "r-b-hip-hop-songs" and entry[3] == True) and not (song_entry.Genre == "dance-electronic-songs" and entry[4] == True) and not (song_entry.Genre == "pop-songs" and entry[5] == True):
            #print("past genre if") 
            if not (db.session.query(models.song_points).filter(models.song_points.SongID == sid, models.song_points.Genre == song_entry.Genre).first() == None):
                print(song_entry.SongID)
                #print("past points if") 
                points = db.session.query(models.song_points).filter(models.song_points.SongID == sid, models.song_points.Genre == song_entry.Genre).first().Points
                path = "/" + song_entry.Genre  + "/" + s[0].Artist + "/" + song_entry.Title + "/"
                try:
                    result = firebase.put(path, "points", points)
                except:
                    pass
                #print(result)
                path = "/" + song_entry.Genre  + "/" + song_entry.Artist + "/" + song_entry.Title + "/graphs/" 
                urls = db.session.query(models.graphs).filter(models.graphs.SongID == sid).all()
                for u in urls:
                    try:
                        result = firebase.put(path, "url" + str(URL_NUM) , tls.get_embed(u.URL))
                        URL_NUM  += 1
                    except:
                        pass
                    #print(result)
                URL_NUM = 0
        if song_entry.Genre == "hot-100":
            entry[0] = True
        if song_entry.Genre == "country-songs":
            entry[1] = True
        if song_entry.Genre == "rock-songs":
            entry[2] = True
        if song_entry.Genre == "r-b-hip-hop-songs":
            entry[3] = True
        if song_entry.Genre == "dance-electronic-songs":
            entry[4] = True
        if song_entry.Genre == "pop-songs":
            entry[5] = True
        
        
