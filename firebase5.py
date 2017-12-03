from app import db, models
from datetime import datetime
import unicodedata
firebasefile = open('FirebaseSongFile2.json','a')
MINYEAR=1960
MAXYEAR=1976
#firebasefile.write(',\t"SongPointsByYear" : {\n')
for j in range(MINYEAR, MAXYEAR):
	if j%5==0:
		for i in range(j+1, MAXYEAR):
			if i%5==0:
				year1 = datetime.strptime(str(j)+"-01-01", "%Y-%m-%d")
				year2 = datetime.strptime(str((i+1)*5)+"-01-01", "%Y-%m-%d")
				firebasefile.write('\t\t"'+str(j)+' - '+str(i)+'" : [null,{\n')	
				print('\t\t"'+str(j)+' - '+str(i)+'" : [null,{\n')
				DateRange = db.session.query(models.songs).filter(models.songs.Date>year1, models.songs.Date<year2).group_by(models.songs.SongID).all()
				SongIDS=[]
				Points={}
				for SongID in DateRange:
					SongIDS.append(SongID.SongID)
				for SongID in SongIDS:
					PointsToCompile= db.session.query(models.songs).filter(models.songs.Date>year1, 
						models.songs.Date<year2, models.songs.SongID == SongID)
					individualPoints=0
					for PointsByWeek in PointsToCompile:
						individualPoints+=PointsByWeek.Points
					Points[SongID]=individualPoints
				Points = sorted(Points.iteritems(), key=lambda (k,v): (v,k))
				Points.reverse()
				for key, value in Points:
					Song = db.session.query(models.songs).filter(models.songs.SongID==key).first()
					firebasefile.write('\t\t\t"Points" :' + str(value)+',\n'
							   '\t\t\t"SongID" :' + str(key)+',\n'
							   '\t\t\t"SongName" : "' + unicodedata.normalize('NFKD', Song.Title.replace('"','\'')).encode('ascii', 'ignore')+'" ,\n'
							   '\t\t\t"Artist" : "' + unicodedata.normalize('NFKD', Song.Artist.replace('"','\'')).encode('ascii', 'ignore')+'" \n\t\t},{\n')
				firebasefile.write('\t}],')
			
			
