from app import db, models
from datetime import datetime
import unicodedata
firebasefile = open('FirebaseSongFile1.json','a')
MINYEAR=1958
MAXYEAR=2018
firebasefile.write(',\t"ArtistPointsByYear" : {\n')
for i in range(MINYEAR, MAXYEAR):
	year1 = datetime.strptime(str(i)+"-01-01", "%Y-%m-%d")
	year2 = datetime.strptime(str(i+1)+"-01-01", "%Y-%m-%d")
        print year1
	firebasefile.write('\t\t"'+str(i)+'" : [null,{\n')
	DateRange = db.session.query(models.songs).filter(models.songs.Date>year1, models.songs.Date<year2).group_by(models.songs.ArtistID)
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
		
			
