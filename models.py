from app import db

class songs(db.Model):
        SongID = db.Column(db.Integer, primary_key=True)
        ArtistID = db.Column(db.Integer)
        Artist = db.Column(db.String(120))
        Title = db.Column(db.String(120))
        Genre = db.Column(db.String(25), primary_key=True)
        Points = db.Column(db.Integer)
        Date = db.Column(db.Date, primary_key=True)
        def __repr__self(self):
                return "%d, %s, %d\n" %(self.SongID, self.Genre, self.Points)

class artist_points(db.Model):
        Artist = db.Column(db.String(120), primary_key=True)
        ArtistID = db.Column(db.Integer)
        Genre = db.Column(db.String(25), primary_key=True)
        Points = db.Column(db.Integer)
        def __repr__self(self):
            return "%s, %d\n" %(self.Artist, self.Points)

class graphs(db.Model):
        SongID = db.Column(db.String(120), primary_key = True)
        URL = db.Column(db.String(120), primary_key=True)
        def __repr__self(self):
            return "%s, %s\n" %(self.SongID, self.URL)

class song_points(db.Model):
        SongID = db.Column(db.Integer, primary_key = True)
        ArtistID = db.Column(db.Integer)
        Artist = db.Column(db.String(120))
        Genre = db.Column(db.String(25), primary_key = True)
        Points = db.Column(db.Integer)
