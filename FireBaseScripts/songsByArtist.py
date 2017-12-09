from app import db, models
from datetime import datetime
import unicodedata
firebasefile = open('FirebaseSongFile2.json','w')
MINYEAR=1958
MAXYEAR=2018
firebasefile.write(',\t"SongsByArtist" : {\n')
for i in range(MINYEAR, MAXYEAR):
	year1 = datetime.strptime(str(i)+"-01-01", "%Y-%m-%d")
	year2 = datetime.strptime(str(i+1)+"-01-01", "%Y-%m-%d")
        print year1
	firebasefile.write('\t\t"'+str(i)+'" : {\n')
	DateRange = db.session.query(models.songs).filter(models.songs.Date>year1, models.songs.Date<year2)
        ArtistIDS=[]
        for ArtistID in DateRange:
            if ArtistID.ArtistID not in ArtistIDS:     
                ArtistIDS.append(ArtistID.ArtistID)
	for ArtistID in ArtistIDS:
	        SongIDS=[]
	        Points={}
		PointsToCompile= db.session.query(models.songs).filter(models.songs.Date>year1, 
			models.songs.Date<year2, models.songs.ArtistID == ArtistID)
                firebasefile.write('\t\t\t"'+unicodedata.normalize('NFKD', PointsToCompile.first().Artist.replace('"','\'')).encode('ascii', 'ignore')+'" : {\n')
                for song in PointsToCompile:
                    if song.SongID not in SongIDS:
                        SongIDS.append(song.SongID)
                for song in SongIDS:
                    #print "SONGID", song;
                    songPoints = db.session.query(models.songs).filter(models.songs.Date>year1,
                            models.songs.Date<year2, models.songs.ArtistID==ArtistID,
                            models.songs.SongID==song)
		    individualPoints=0
                    for points in songPoints:
                        #print "NAME OF SONG",points.Title
			individualPoints+=points.Points

                    #print individualPoints
		    Points[song]=individualPoints
	        Points = sorted(Points.iteritems(), key=lambda (k,v): (v,k))
	        Points.reverse()
	        for key in Points:
		    Song = db.session.query(models.songs).filter(models.songs.SongID==key[0]).first()
                    firebasefile.write('\t\t\t\t"' + unicodedata.normalize('NFKD', Song.Title.replace('"','\'')).encode('ascii', 'ignore')+'" : {\n')
                    if key != Points[-1]:
		        firebasefile.write('\t\t\t\t"Points" :' + str(key[1])+',\n'
				   '\t\t\t\t"SongID" :' + str(key[0])+'}\n\t\t\t,')
                    else:
		        firebasefile.write('\t\t\t\t"Points" :' + str(key[1])+',\n'
				   '\t\t\t\t"SongID" :' + str(key[0])+'}\n')
                if ArtistID!= ArtistIDS[-1]:
                    firebasefile.write('\n\t\t},')
                else:
                    firebasefile.write('\n\t\t}')
                        
        firebasefile.write('\t},')
		
			
