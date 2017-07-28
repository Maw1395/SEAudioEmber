import billboard
import plotly.plotly as py
import plotly.graph_objs as go
from datetime import datetime, timedelta
from app import db, models
import dateutil.parser as parser 
from sqlalchemy import exists, desc
import config as Config


py.sign_in(Config.user, Config.api_key)
song_id_max=0
artist_id_max=0
def insertsongs(NAMEOFGENRE):
    global song_id_max
    global artist_id_max


    chart = billboard.ChartData(NAMEOFGENRE, date = '2016-01-01')
    while(chart.date < '2017-07-24'):
        
        for i in chart:
            date = datetime.strptime(chart.date, '%Y-%m-%d')
            artist = i.artist
            print (i.title)
            print (artist)
            
            #-------------Calculating Point Position------------------------------
            Points = 101
            for b in range(i.rank):
                Points = Points - 1
            #-------------------checking Song Table for same songID----------------------------
            if ((db.session.query(models.songs).filter(models.songs.Title==i.title, models.songs.Artist==artist).count())==0):
                song_id =song_id_max+1
                song_id_max+=1
            else:
                song_id=db.session.query(models.songs).filter(models.songs.Title==i.title, models.songs.Artist==artist).first().SongID
            
            #-------------------checking Song Table for same ArtistID----------------------------
            if ((db.session.query(models.songs).filter(models.songs.Artist==artist).count())==0):
                artist_id=artist_id_max+1
                artist_id_max=artist_id_max+1
            else:
                artist_id=db.session.query(models.songs).filter(models.songs.Artist==artist).first().ArtistID
            
            SongToAdd = models.songs(Artist=artist, Title = i.title, SongID=song_id, Genre=NAMEOFGENRE,Points= Points, Date = date, ArtistID=artist_id)

            db.session.add(SongToAdd)
        
        newdate = date + timedelta(days = 7)
        chart=billboard.ChartData(NAMEOFGENRE, newdate.strftime("%Y-%m-%d")) 
def CalculateSongPoints():
    global song_id_max
    global artist_id_max
    song_id_max=2133
    artist_id_max=1288
    currentSongID=0
    while(song_id_max>currentSongID):
        nameOfSong = db.session.query(models.songs).filter(models.songs.SongID==currentSongID).all()
        HOT100Points=0
        CountryPoints=0
        RBPoints=0
        RockPoints=0
        EDMPoints=0
        PopPoints=0
        for i in nameOfSong:
            if(i.Genre=='hot-100'):
                HOT100Points=HOT100Points+i.Points
            if(i.Genre=='country-songs'): 
                CountryPoints=CountryPoints+i.Points
            if(i.Genre=='r-b-hip-hop-songs'): 
                RBPoints=RBPoints+i.Points
            if(i.Genre=='rock-songs'):
                RockPoints=RockPoints+i.Points
            if(i.Genre=='dance-electronic-songs'):
                EDMPoints=EDMPoints+i.Points
            if(i.Genre=='pop-songs'):
                PopPoints=PopPoints+i.Points
        if(HOT100Points>0):
            SongPointsToAdd= models.song_points( SongID=nameOfSong[0].SongID, Genre="hot-100", Points=HOT100Points, ArtistID=nameOfSong[0].ArtistID)
            print (i.Title)
            print("Hot-100")
            db.session.add(SongPointsToAdd)
        if(CountryPoints>0):
            print (i.Title)
            print("Country")
            SongPointsToAdd= models.song_points( SongID=nameOfSong[0].SongID, Genre="country-songs", Points=CountryPoints, ArtistID=nameOfSong[0].ArtistID)
            db.session.add(SongPointsToAdd)
        if(RBPoints>0):
            print (i.Title)
            print("R-B")
            SongPointsToAdd= models.song_points( SongID=nameOfSong[0].SongID, Genre="r-b-hip-hop-songs", Points=RBPoints, ArtistID=nameOfSong[0].ArtistID)
            db.session.add(SongPointsToAdd)
        if(RockPoints>0):
            print (i.Title)
            print("Rock")
            SongPointsToAdd= models.song_points( SongID=nameOfSong[0].SongID, Genre="rock-songs", Points=RockPoints, ArtistID=nameOfSong[0].ArtistID)
            db.session.add(SongPointsToAdd)
        if(EDMPoints>0):
            print (i.Title)
            print("EDM")
            SongPointsToAdd= models.song_points( SongID=nameOfSong[0].SongID, Genre="dance-electronic-songs", Points=EDMPoints, ArtistID=nameOfSong[0].ArtistID)
            db.session.add(SongPointsToAdd)
        if(PopPoints>0):
            print (i.Title)
            print("Pop")
            SongPointsToAdd= models.song_points( SongID=nameOfSong[0].SongID, Genre="pop-songs", Points=PopPoints, ArtistID=nameOfSong[0].ArtistID)
            db.session.add(SongPointsToAdd)
        currentSongID+=1
def CalculateArtistPoints():
    global song_id_max
    global artist_id_max
    song_id_max = 2133
    artist_id_max = 1288
    currentArtistID=1
    while(currentArtistID<artist_id_max):
        artistEntries = db.session.query(models.song_points).filter(models.song_points.ArtistID==currentArtistID).order_by(models.song_points.SongID.desc())
        maxPointsForAllGenres=0
        cumPointsArrayByGenre=[0,0,0,0,0,0]
        currentPointsArrayByGenre=[0,0,0,0,0,0]
        GenreNames = ["hot-100", "country-songs", "rock-songs", "r-b-hip-hop-songs", "dance-electronic-songs", "pop-songs"]

        for i in artistEntries:
            for j in range(0,6):
                if i.Genre==GenreNames[j]:
                    cumPointsArrayByGenre[j]+=i.Points
                    currentPointsArrayByGenre[j]=i.Points
            maxPointsForAllGenres+=max(currentPointsArrayByGenre)
        ArtistName =  db.session.query(models.songs).filter(models.songs.ArtistID==currentArtistID).first().Artist

        ArtistPointsToAdd=models.artist_points(Artist=ArtistName, Points=maxPointsForAllGenres, Genre="ALL",)
        db.session.add(ArtistPointsToAdd)
        currentArtistID+=1
        for i in range(0,6):
            if (cumPointsArrayByGenre[i]!=0):
                ArtistPointsToAdd = models.artist_points(Artist=ArtistName, Points=cumPointsArrayByGenre[i],Genre=GenreNames[i])
                db.session.add(ArtistPointsToAdd)
                print (GenreNames[i])
                print(ArtistName)

        currentArtistID=currentArtistID+1
            
def GraphinFunctionByGenre(songID, Genres):
    results = db.session.query(models.songs).filter(models.songs.SongID == songID, models.songs.Genre == Genres).all()
    weeks = []
    pointsbyweek=[]
    prevWeek=results[0].Date
    LastDate = '2017-07-24'
    for songByWeek in results:
        while(songByWeek.Date>prevWeek+timedelta(days = 7)):
            pointsbyweek.append(0)
            weeks.append(prevWeek+timedelta(days=7))
            prevWeek+=timedelta(days=7)
        pointsbyweek.append(songByWeek.Points)
        weeks.append(songByWeek.Date)
        prevWeek=songByWeek.Date
    tracebyweek = go.Scatter(
            x=weeks,
            y=pointsbyweek
            )
    if Genres == "hot-100":
        layout1 = go.Layout(
                title = results[0].Title.upper() + "'S POINTS IN THE " + Genres.upper() + " CHART",
                xaxis = dict(
                    title ="Date"),
                yaxis = dict(
                    range=[0,110],
                    showticklabels = False)
                )
    elif Genres =="pop-songs":
        layout1 = go.Layout(
            title=results[0].Title.upper() + "'S POINTS IN THE " + Genres.upper() + " CHART",
            xaxis=dict(
                title="Date"),
            yaxis=dict(
                range=[60, 104],
                showticklabels=False)
        )
    else:
        layout1 = go.Layout(
            title=results[0].Title.upper() + "'S POINTS IN THE " + Genres.upper() + " CHART",
            xaxis=dict(
                title="Date"),
            yaxis=dict(
                range=[50, 105],
                showticklabels=False)
        )
    data2=[tracebyweek]
    fig = go.Figure(data =data2, layout = layout1)
    ByWeek =  "Song_By_Week_" + Genres.upper() + str(songID)

    #py.plot(data, filename = Overall, auto_open=False)
    file1 = py.plot(fig, filename = ByWeek, auto_open = False)
    u = models.graphs(SongID=songID, URL=file1)
    db.session.add(u)
    print (Genres.upper())
    print (songID)

def GraphingFunction():
    song_id_max = 2133
    artist_id_max = 1288
    for song_id in range(1634,song_id_max+1):
        a = db.session.query(models.song_points).filter(models.song_points.SongID==song_id).all()
        country = False
        hot = False
        pop = False
        rb = False
        edm = False
        rock = False
        for b in a:
            if(b.Genre == 'pop-songs'):
                pop=True
            if(b.Genre == 'rock-songs'):
                rock=True
            if(b.Genre == 'hot-100'):
                hot=True
            if(b.Genre == 'dance-electronic-songs'):
                edm=True
            if(b.Genre == 'r-b-hip-hop-songs'):
                rb=True
            if(b.Genre == 'country-songs'):
                country=True
        if(hot):
            GraphinFunctionByGenre(a[0].SongID, 'hot-100')
        if(pop):
            GraphinFunctionByGenre(a[0].SongID, 'pop-songs')
        if(rock):
            GraphinFunctionByGenre(a[0].SongID, 'rock-songs')
        if(edm):
            GraphinFunctionByGenre(a[0].SongID, 'dance-electronic-songs')
        if(rb):
            GraphinFunctionByGenre(a[0].SongID, 'r-b-hip-hop-songs')
        if(country):
            GraphinFunctionByGenre(a[0].SongID, 'country-songs')
        db.session.commit()

'''insertsongs('hot-100')
db.session.commit()
insertsongs('country-songs')
db.session.commit()
insertsongs('r-b-hip-hop-songs')
db.session.commit()
insertsongs('rock-songs')
db.session.commit()
insertsongs('dance-electronic-songs')
db.session.commit()
insertsongs('pop-songs')
db.session.commit()'''
'''CalculateSongPoints()
db.session.commit()'''
'''CalculateArtistPoints()
db.session.commit()'''
GraphingFunction()
db.session.commit()
