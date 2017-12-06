from app import db, models
from datetime import datetime
import unicodedata
firebasefile = open('FirebaseSongFile1.json','a')
MINYEAR=1958
MAXYEAR=2018
genre = ['rock-songs','pop-songs','r-b-hip-hop-songs','hot-100','dance-club-play-songs', 'country-songs']
for GENRE in genre:
    firebasefile.write(',\t"ArtistPointsByYear-'+ GENRE+'" : {\n')
    for i in range(MINYEAR, MAXYEAR):
            year1 = datetime.strptime(str(i)+"-01-01", "%Y-%m-%d")
            year2 = datetime.strptime(str(i+1)+"-01-01", "%Y-%m-%d")
            DateRange = db.session.query(models.songs).filter(models.songs.Date>year1, models.songs.Date<year2, models.songs.Genre==GENRE).group_by(models.songs.ArtistID)
            if(DateRange.first()):
                firebasefile.write('\t\t"'+str(i)+'" : [null,{\n')
                print GENRE, year1
                SongIDS=[]
                Points={}
                for SongID in DateRange:
                        if SongID.ArtistID not in SongIDS:
                                SongIDS.append(SongID.ArtistID)
                for SongID in SongIDS:
                        PointsToCompile= db.session.query(models.songs).filter(models.songs.Date>year1, 
                                models.songs.Date<year2, models.songs.ArtistID == SongID)
                        individualPoints=0
                        for PointsByWeek in PointsToCompile:
                                individualPoints+=PointsByWeek.Points
                        Points[SongID]=individualPoints
                Points = sorted(Points.iteritems(), key=lambda (k,v): (v,k))
                Points.reverse()
                for key, value in Points:
                        Song = db.session.query(models.songs).filter(models.songs.ArtistID==key).first()
                        firebasefile.write('\t\t\t"Points" :' + str(value)+',\n'
                                           '\t\t\t"Artist" : "' + unicodedata.normalize('NFKD', Song.Artist.replace('"','\'')).encode('ascii', 'ignore')+'" \n\t\t},{\n')
                firebasefile.write('\t}],')
    firebasefile.write('},')
                    
                            
