from app import db, models
from datetime import datetime, timedelta 

Firebasefile= open('FirebaseSongFile1.json','w')
Firebasefile.write("{\n")
Firebasefile.write('\t"SongByDay1" : [null,{\n')

SONG_MAX = db.session.query(models.songs).order_by(models.songs.SongID.desc()).first().SongID                    
SONG_MIN=1
GenreList = ['hot-100', 'country-songs', 'rock-songs', 'r-b-hip-hop-songs', 'dance-club-play-songs', 'pop-songs']
for i in range(1, SONG_MAX):
    if i%1000 == 0:
	print i
    SONG_MIN=i
    songEntry = i
    count = 0
    for j in range(len(GenreList)):
        IndividualSongList = db.session.query(models.songs).filter(models.songs.SongID == songEntry,
                                                                   models.songs.Genre == GenreList[j]    )
        if IndividualSongList.count()>0:
            count+=1
    counter=0
    for j in range(len(GenreList)):
        IndividualSongList = db.session.query(models.songs).filter(models.songs.SongID == songEntry,
                                                                   models.songs.Genre == GenreList[j]    )
        IndividualSongListCount = db.session.query(models.songs).filter(models.songs.SongID == songEntry,
                                                                   models.songs.Genre == GenreList[j]).count()
        if IndividualSongList.count()>0:
            Firebasefile.write('\t\t\t"'+GenreList[j]+'" : {\n')
            for k in range (0,IndividualSongListCount-1):
                Date = IndividualSongList[k].Date
                Datestr = Date.strftime('%Y-%m-%d')
                Firebasefile.write('\t\t\t\t"'+Datestr+'" : {\n')
                Firebasefile.write('\t\t\t\t\t"Points" : '+str(IndividualSongList[k].Points)+'\n\t\t\t\t},\n')
            Date = IndividualSongList[IndividualSongListCount-1].Date
            Datestr = Date.strftime('%Y-%m-%d')
            Firebasefile.write('\t\t\t\t"'+Datestr+'" : {\n')
            Firebasefile.write('\n\t\t\t\t\t"Points" : '+str(IndividualSongList[IndividualSongList.count()-1].Points)+'\n\t\t\t\t}\n')
            counter+=1
            if counter!=count:
                Firebasefile.write('\t\t\t},\n')
            else:
                Firebasefile.write('\t\t\t}\n\t\t}, {\n')
 
                #Points = IndividualSongEntry.Points
                #Artist = unicodedata.normalize('NFKD', IndividualSongEntry.Artist).encode('ascii', 'ignore')
                #Title = unicodedata.normalize('NFKD', IndividualSongEntry.Title).encode('ascii', 'ignore')
                #SongID = IndividualSongEntry.SongID
                #path = "SongByDay/" + str(SongID) + '/' + GenreList[j] + '/' + Date.strftime('%Y-%m-%    d')
                

